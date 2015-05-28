/**
 * 
 */
package io.scheduler.data.handler;

import io.scheduler.data.Course;
import io.scheduler.data.DegreeCourse;
import io.scheduler.data.DegreeReq;
import io.scheduler.data.Program;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author skarahoda
 *
 */
public class DegreeParser {

	/**
	 * constant URL for web site of degree requirements.
	 */
	private static final String degreeUrlTemplate = "http://www.sabanciuniv.edu/en/prospective-students/degree-detail?SU_DEGREE.p_degree_detail?P_TERM=%d&P_PROGRAM=%s&P_SUBMIT=&P_LANG=EN&P_LEVEL=%s";
	private static final String courseDegreeUrlTemplate = "http://www.sabanciuniv.edu/en/prospective-students/degree-detail?SU_DEGREE.p_list_courses?P_TERM=%d&P_AREA=%s&P_PROGRAM=%s&P_LANG=EN&P_LEVEL=%s";

	public static void parse(int term, boolean isUG, String pName)
			throws IOException, IllegalArgumentException, SQLException {
		Program p = new Program(term, pName, isUG);

		// Web site connection
		String degreeUrl = String.format(degreeUrlTemplate, term, pName,
				isUG ? "UG" : "G");
		Document doc = Jsoup.connect(degreeUrl).maxBodySize(0).get();
		Elements rows = doc.select("table.t_mezuniyet").first().children()
				.select("tr:gt(1)");
		for (Element element : rows) {
			parseRow(element, p);
		}
	}

	private static void parseRow(Element element, Program p)
			throws SQLException, IOException {

		String req_name = element.child(0).text();
		int credit;
		try {
			credit = Integer.parseInt(element.child(2).text());
		} catch (NumberFormatException e) {
			credit = 0;
		}
		int course_num;
		try {
			course_num = Integer.parseInt(element.child(3).text());
		} catch (NumberFormatException e) {
			course_num = 0;
		}
		Element linkElement = element.child(0).child(0);
		if (linkElement != null) {
			String url = linkElement.attr("href");
			if (!url.equals("")) {
				url = url.substring(1);
				DegreeReq degree = DegreeReq.get(course_num, credit, req_name,
						url, p);
				parseCourse(degree);
			} else {
				DegreeReq.get(course_num, credit, req_name, p);
			}
		} else {
			DegreeReq.get(course_num, credit, req_name, p);
		}
	}

	private static void parseCourse(DegreeReq degree) throws IOException,
			SQLException {
		String url = createUrl(degree);
		System.out.println(url);
		Document doc = Jsoup.connect(url).maxBodySize(0).get();
		Elements rows = doc.select("table").first().children()
				.select("tr:gt(2)");
		for (Element element : rows) {
			String code = element.child(1).text();
			String name = element.child(2).text();
			float credit = 0;
			credit = Float.parseFloat(element.child(4).text());
			Course c = Course.get(code, name, credit);
			new DegreeCourse(degree, c);
		}
	}

	private static String createUrl(DegreeReq degree) {
		Program p = degree.getProgram();
		String isUG = p.getIsUG() ? "UG" : "G";
		return String.format(courseDegreeUrlTemplate, p.getEnterTerm(),
				degree.getHref(), p.getName(), isUG);
	}

}
