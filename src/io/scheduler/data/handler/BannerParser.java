package io.scheduler.data.handler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
	 */
	public static void getSUClasses(String term) throws IOException{
		
		//bannerweb connection
		String bannerUrl = String.format(bannerUrlTemplate, term);
		Document doc = Jsoup.connect(bannerUrl).get();
		
		
		Elements rows = doc.select("[summary=\"This layout table is used to present the sections found\"]").first().select("tr");
		Elements times;
		Element course;
		for(Element row : rows){
			course = row.children().first();
			if(course.className().equals("ddlabel")){
				System.out.println(course.text());
				times = row.nextElementSibling().select("[summary] td:nth-child(2)");
				for(Element time : times){
					System.out.println(time.text() + " " + time.nextElementSibling().text());
					Element instructor = time.nextElementSibling();
					for(int i=0; i<4;i++){
						instructor =  instructor.nextElementSibling();
					}
					System.out.println(instructor.text());
				}
			}
		}
	}
}
