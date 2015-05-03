package io.scheduler.data.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import io.scheduler.data.SUClass;
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
	 */
	public static List<SUClass> getSUClasses(String term) throws IOException{
		List<SUClass> list = new ArrayList<SUClass>();
		
		//bannerweb connection
		String bannerUrl = String.format(bannerUrlTemplate, term);
		Document doc = Jsoup.connect(bannerUrl).get();
		
		
		Elements rows = doc.select("[summary=\"This layout table is used to present the sections found\"]").first().select("tr");
		//Elements times;
		Element course;
		for(Element row : rows){
			course = row.children().first();
			if(course.className().equals("ddlabel")){
				String instructor = getInstructor(row.nextElementSibling().children().first());
				SUClass temp = createSUCLass(course.text(),instructor, term);
				list.add(temp);
				/*times = row.nextElementSibling().select("[summary] td:nth-child(2)");
				for(Element time : times){
					System.out.println(time.text() + " " + time.nextElementSibling().text());
				}*/
			}
		}
		return list;
	}
	private static SUClass createSUCLass(String courseText, String instructor, String term) {
		
		Scanner scanner = new Scanner(courseText);
		scanner.useDelimiter(" - ");
		String courseName = scanner.next();
		String crn = scanner.next();
		String courseCode = scanner.next();
		String section = scanner.next();
		scanner.close();
		return new SUClass(term, crn, instructor, section);
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
}
