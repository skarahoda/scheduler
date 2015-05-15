package io.scheduler.data.handler;

import io.scheduler.data.Course;
import io.scheduler.data.Meeting;
import io.scheduler.data.SUClass;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
/**
 * This class parse the class list in the bannerweb
 * @author skarahoda
 *
 */
public class BannerParser {
	
	/**
	 * constant URL for web site of course list.
	 */
	private static final String bannerUrlTemplate = "http://hweb.sabanciuniv.edu/schedule%s.html";
	
	/**
	 * @param term: to select term it is combination of year and
	 * 				term which are 01=fall 02=spring 03=summer
	 * 				e.g. 201402 means 2014-2015 Spring term.
	 * 
	 * @throws IOException: if there is an error to connect web site.
	 * @throws SQLException: if there is an error to execute sql query. Database file may be already using.
	 * @throws InterruptedException 
	 */
	public static void getSUClasses(String term) throws IOException, SQLException{
		List<SUClass> suClasses = new ArrayList<SUClass>();
		Collection<Course> courses = DatabaseConnector.get(Course.class);
		Collection<Meeting> meetings = new ArrayList<Meeting>();
		
		//bannerweb connection
		String bannerUrl = String.format(bannerUrlTemplate, term);
		Document doc = Jsoup.connect(bannerUrl)
				.maxBodySize(0)
			    .get();
        
		Elements rows = doc.select("[summary=\"This layout table is used to present the sections found\"]")
				.first().children().get(1).children();
		Iterator<Element> i = rows.iterator();
		
		while(i.hasNext()){
			Element header = i.next();
			Element details = i.next().child(0);
			ParseForSUClass(header, details, suClasses, courses, meetings);
		}
		BannerParser.saveToDb(suClasses, courses, meetings);

	}
	private static void saveToDb(List<SUClass> suClasses,
			Collection<Course> courses, Collection<Meeting> meetings) throws SQLException {
		DatabaseConnector.clearTable(Meeting.class);
		DatabaseConnector.clearTable(SUClass.class);
		DatabaseConnector.createIfNotExist(courses, Course.class);
		DatabaseConnector.create(suClasses, SUClass.class);
		DatabaseConnector.create(meetings, Meeting.class);
	}
	private static void ParseForSUClass(Element header, Element details,
			Collection<SUClass> suClasses, Collection<Course> courses, Collection<Meeting> meetings) {
		
		String[] headerItems = header.text().split(" - ");
		ListIterator<String> i = Arrays.asList(headerItems).listIterator(headerItems.length);
		String section = i.previous();
		String courseCode = i.previous();
		String crn = i.previous();
		String courseName = i.previous();
		while(i.hasPrevious()){
			courseName = i.previous() + " - " + courseName;
		}
		String instructor = BannerParser.getInstructor(details);
		if(instructor.equals(""))
			instructor = "TBA";
		
		boolean courseFound = false;
		SUClass tempSUClass = null;
		for(Course course: courses){
			if(course.getCode().equals(courseCode)){
				tempSUClass = new SUClass(crn, instructor, section, course);
				courseFound = true;
				break;
			}
		}
		
		if(!courseFound){
		Course course = new Course(courseCode, courseName, getCredit(details));
		courses.add(course);
		tempSUClass = new SUClass(crn, instructor, section, course);
		}
		
		suClasses.add(tempSUClass);

		Elements meetingInfos = details.select("tr:has(td)");
		for(Element meeting: meetingInfos){
			Meeting tempMeeting = BannerParser.createMeeting(meeting, tempSUClass);
			meetings.add(tempMeeting);
		}
		return;
	}
	
	private static Meeting createMeeting(Element meeting, SUClass tempSUClass) {
		Elements columns = meeting.children();
		String[] times = columns.get(1).text().split(" - ");
		String day = columns.get(2).text();
		String place = columns.get(3).text();
		if(times[0].equals("TBA")){
			return new Meeting(null,null,"TBA",place,tempSUClass);
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
		return new Meeting(start,end,day,place,tempSUClass);
	}
	private static String getInstructor(Element element){
		boolean canPrint = false;
		String instructor = "";
		for(Node child : element.childNodes()){
			if(canPrint)
				instructor += child.toString();
			if(child.toString().contains("Instructors:"))
				canPrint = true;
			if(canPrint && child.toString().equals("<br>"))
				break;
		}
		instructor = Jsoup.parse(instructor).text();
		return instructor;
	}
	
	private static float getCredit(Element element){
		for(Node child : element.childNodes()){
			if(child.toString().contains("Credits")){
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
