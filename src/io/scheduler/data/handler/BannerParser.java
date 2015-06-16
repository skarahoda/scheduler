package io.scheduler.data.handler;

import io.scheduler.data.Course;
import io.scheduler.data.Meeting;
import io.scheduler.data.Requisite;
import io.scheduler.data.SUClass;
import io.scheduler.data.Term;
import io.scheduler.data.User;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * This class parse the class list in the bannerweb
 * 
 * @author skarahoda
 *
 */
public class BannerParser {

	/**
	 * constant URL for web site of course list.
	 */
	private static final String bannerUrlTemplate = "http://hweb.sabanciuniv.edu/schedule%d.html";
	private static final String suClassUrlTemplate = "http://suis.sabanciuniv.edu/prod/bwckschd.p_disp_detail_sched?term_in=%d&crn_in=%s";

	/**
	 * @param term
	 *            : to select term it is combination of year and term which are
	 *            01=fall 02=spring 03=summer e.g. 201402 means 2014-2015 Spring
	 *            term.
	 * 
	 * @throws IOException
	 *             : if there is an error to connect web site.
	 * @throws SQLException
	 *             : if there is an error to execute sql query. Database file
	 *             may be already using.
	 * @throws InterruptedException
	 */
	public static void parse(Term term) throws IOException, SQLException,
			IllegalArgumentException {
		Term oldTerm = User.getCurrentTerm();
		try {
			User.setCurrentTerm(term);
			Collection<Course> courses = Course.getAll();
			// bannerweb connection
			String bannerUrl = String.format(bannerUrlTemplate, term.toInt());
			Document doc = Jsoup.connect(bannerUrl).maxBodySize(0).get();

			Elements rows = doc
					.select("[summary=\"This layout table is used to present the sections found\"]")
					.first().children().get(1).children();
			Iterator<Element> i = rows.iterator();

			while (i.hasNext()) {
				Element header = i.next();
				Element details = i.next().child(0);
				ParseForSUClass(header, details, courses, term);
			}
		} catch (Exception e) {
			User.setCurrentTerm(oldTerm);
			throw e;
		}
	}

	private static void ParseForSUClass(Element header, Element details,
			Collection<Course> courses, Term term) throws SQLException,
			IOException {

		String[] headerItems = header.text().split(" - ");
		ListIterator<String> i = Arrays.asList(headerItems).listIterator(
				headerItems.length);
		String section = i.previous();
		String courseCode = i.previous();
		String crn = i.previous();
		String courseName = i.previous();
		while (i.hasPrevious()) {
			courseName = i.previous() + " - " + courseName;
		}
		String instructor = BannerParser.getInstructor(details);
		if (instructor.equals(""))
			instructor = "TBA";
		Course course = Course.get(courseCode);
		if (course == null) {
			course = Course.create(courseCode, courseName, getCredit(details));
		}
		if (!course.isCheckedForReq()) {
			getRequisites(course, term, crn);
		}
		SUClass suClass = SUClass
				.create(crn, instructor, section, course, term);
		Elements meetingInfos = details.select("tr:has(td)");
		for (Element meeting : meetingInfos) {
			BannerParser.createMeeting(meeting, suClass);
		}
	}

	private static void getRequisites(Course c, Term term, String crn)
			throws IOException, SQLException {
		String url = String.format(suClassUrlTemplate, term.toInt(), crn);
		Document doc = Jsoup.connect(url).timeout(0).maxBodySize(0).get();
		Element prereqElement = doc.select("span:contains(Prerequisites:)")
				.first();
		String prereqText = "";
		if (prereqElement != null) {
			for (Node sibling : prereqElement.siblingNodes()) {
				if (sibling.siblingIndex() > prereqElement.siblingIndex()) {
					prereqText += sibling.toString();
				}
			}
		}
		Element coreqElemenet = doc.select("span:contains(Corequisites:)")
				.first();
		String coreqText = "";
		if (coreqElemenet != null) {
			for (Node sibling : coreqElemenet.siblingNodes()) {
				if (sibling.siblingIndex() > coreqElemenet.siblingIndex()
						&& (prereqElement == null || sibling.siblingIndex() < prereqElement
								.siblingIndex())) {
					coreqText += sibling.toString();
				}
			}
		}
		Requisite preReq = prereqText.equals("") ? null
				: parseRequisite(prereqText);
		Requisite coReq = coreqText.equals("") ? null
				: parseRequisite(coreqText);
		c.setReqs(preReq, coReq);
	}

	private static Requisite parseRequisite(String fullText)
			throws SQLException {
		Elements courses = Jsoup.parse(fullText).select("a");
		Stack<Boolean> operators = new Stack<Boolean>();
		Stack<Requisite> requisites = new Stack<Requisite>();
		for (Element reqCourse : courses) {
			Node previous = reqCourse.previousSibling();
			requisites.push(new Requisite(reqCourse.text()));
			if ((previous == null || !previous.toString().contains("("))
					&& !operators.isEmpty()) {
				Requisite right = requisites.pop();
				Requisite left = requisites.pop();
				requisites.push(new Requisite(left.getId(), right.getId(),
						operators.pop()));
			}
			Node next = reqCourse.nextSibling();
			if (next != null) {
				String text = next.toString();
				if (text.contains(")")) {
					if (!operators.isEmpty()) {
						Requisite right = requisites.pop();
						Requisite left = requisites.pop();
						requisites.push(new Requisite(left.getId(), right
								.getId(), operators.pop()));
					}
				}
				if (text.contains("and")) {
					operators.push(true);
				} else if (text.contains("or")) {
					operators.push(false);
				}
			}
		}
		if (requisites.isEmpty()) {
			return null;
		} else {
			return requisites.pop();
		}
	}

	private static void createMeeting(Element meeting, SUClass tempSUClass)
			throws SQLException {
		Elements columns = meeting.children();
		String[] times = columns.get(1).text().split(" - ");
		String day = columns.get(2).text();
		String place = columns.get(3).text();
		if (times[0].equals("TBA")) {
			return;
		}
		DateFormat format = new SimpleDateFormat("h:mm a");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(times[0]);
			end = format.parse(times[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (start != null && end != null)
			Meeting.createForDb(start, end, Meeting.stringToDay(day), place,
					tempSUClass);
	}

	private static String getInstructor(Element element) {
		boolean canPrint = false;
		String instructor = "";
		for (Node child : element.childNodes()) {
			if (canPrint)
				instructor += child.toString();
			if (child.toString().contains("Instructors:"))
				canPrint = true;
			if (canPrint && child.toString().equals("<br>"))
				break;
		}
		instructor = Jsoup.parse(instructor).text();
		return instructor;
	}

	private static float getCredit(Element element) {
		for (Node child : element.childNodes()) {
			if (child.toString().contains("Credits")) {
				Scanner scanner = new Scanner(child.toString());
				scanner.useDelimiter(" ");
				float credit = scanner.nextFloat();
				scanner.close();
				return credit;
			}
		}
		return 0;
	}
}
