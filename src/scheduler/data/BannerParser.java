package scheduler.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BannerParser {
	public static String getCourses(String term){
		String bannerUrl = "http://hweb.sabanciuniv.edu/schedule%s.html";
		bannerUrl = String.format(bannerUrl, term);
		System.out.println(bannerUrl);
		try {
			Document doc = Jsoup.connect(bannerUrl).get();
			Elements courses = doc.getElementsByClass("ddlabel");
			Elements tables = doc.select("[summary=\"This table lists the scheduled meeting times and assigned instructors for this\"] tr:nth-child(2)");
			for(Element course : courses){
				System.out.println(course.text());
			}
			for(Element table : tables){
				System.out.println(table.text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bannerUrl;
	}
	public static void getCourses(){
		File input = new File("/home/skarahoda/Desktop/list.html");	
		try {
			FileWriter output = new FileWriter("deneme.txt");
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements rows = doc.select("[summary=\"This layout table is used to present the sections found\"]").first().select("tr");
			Elements times;
			Element course;
			for(Element row : rows){
				course = row.children().first();
				if(course.className().equals("ddlabel")){
					output.write(course.text()+"\n");
					times = row.nextElementSibling().select("[summary] td:nth-child(2)");
					for(Element time : times){
						output.write(time.text() + "\n");
					}
				}
			}
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
