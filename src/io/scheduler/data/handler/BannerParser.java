package io.scheduler.data.handler;

import io.scheduler.data.Course;
import io.scheduler.data.SUClass;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	 */
	public static void getSUClasses(String term) throws IOException, SQLException{
		List<SUClass> suClasses = new ArrayList<SUClass>();
		Collection<Course> courses = DatabaseConnector.getCourses();
		//bannerweb connection
		String bannerUrl = String.format(bannerUrlTemplate, term);
		Document doc = Jsoup.connect(bannerUrl).get();
		
		
		Elements rows = doc.select("[summary=\"This layout table is used to present the sections found\"]").first().select("tr");
		//Elements times;
		Element course;
		for(Element row : rows){
			course = row.children().first();
			if(course.className().equals("ddlabel")){
				SUClass temp = createSUCLass(course.text(),row.nextElementSibling().children().first(), term, courses);
				suClasses.add(temp);
				/*times = row.nextElementSibling().select("[summary] td:nth-child(2)");
				for(Element time : times){
					System.out.println(time.text() + " " + time.nextElementSibling().text());
				}*/
			}
		}
		DatabaseConnector.setSUClasses(suClasses);
		DatabaseConnector.setCourses(courses);
	}
	private static SUClass createSUCLass(String courseText, Element courseInfo, String term, Collection<Course> courses) {
		
		Scanner scanner = new Scanner(courseText);
		scanner.useDelimiter(" - ");
		String courseName = scanner.next();
		String crn = scanner.next();
		if(!crn.matches("\\d*")){
			courseName += " - " + crn;
			crn = scanner.next();
		}
		String courseCode = scanner.next();
		String section = scanner.next();
		scanner.close();
		String instructor = getInstructor(courseInfo);
		
		for(Course course: courses){
			if(course.getCode().equals(courseCode)){
				return new SUClass(term, crn, instructor, section, course);
			}
		}
		Course course = new Course(courseCode, courseName, getCredit(courseInfo));
		courses.add(course);
		return new SUClass(term, crn, instructor, section, course);
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
