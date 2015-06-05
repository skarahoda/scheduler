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
import java.util.ArrayList;
import java.util.List;

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
	private static final String fensCourses = "http://www.sabanciuniv.edu/en/prospective-students/degree-detail?SU_DEGREE.p_list_courses?P_TERM=%d&P_AREA=FC_FENS&P_PROGRAM=%s&P_FAC=E&P_LANG=EN&P_LEVEL=UG";
	private static final String fassCourses = "http://www.sabanciuniv.edu/en/prospective-students/degree-detail?SU_DEGREE.p_list_courses?P_TERM=%d&P_AREA=FC_FASS&P_PROGRAM=%s&P_FAC=S&P_LANG=EN&P_LEVEL=UG";
	private static final String somCourses = "http://www.sabanciuniv.edu/en/prospective-students/degree-detail?SU_DEGREE.p_list_courses?P_TERM=%d&P_AREA=FC_SOM&P_PROGRAM=%s&P_FAC=M&P_LANG=EN&P_LEVEL=UG";

	public static Program parse(int term, boolean isUG, String pName)
			throws IOException, IllegalArgumentException, SQLException {
		Program p = Program.get(term, pName, isUG);

		// Web site connection
		String degreeUrl = String.format(degreeUrlTemplate, term, pName,
				isUG ? "UG" : "G");
		Document doc = Jsoup.connect(degreeUrl).maxBodySize(0).timeout(0).get();
		Elements rows = doc.select("table.t_mezuniyet").first().children()
				.select("tr:gt(1)");
		for (Element element : rows) {
			parseRow(element, p);
		}
		return p;
	}

	private static String parseRow(Element element, Program p)
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
				return url;
			} else {
				DegreeReq.get(course_num, credit, req_name, p);
			}
		} else {
			DegreeReq.get(course_num, credit, req_name, p);
		}
		return null;
	}

	private static void parseCourse(DegreeReq degree) throws IOException,
			SQLException {
		List<String> urls = createUrl(degree);
		for (String url : urls) {
			Document doc = Jsoup.connect(url).maxBodySize(0).timeout(0).get();
			Elements rows = doc.select("table").first().children()
					.select("tr:gt(2)");
			System.out.println(degree.getName());
			for (Element element : rows) {
				if(element.children().size() < 3)
					continue;
				String code = element.child(1).text();
				String name = element.child(2).text();
				float credit = 0;
				credit = Float.parseFloat(element.child(4).text());
				Course c = Course.get(code, name, credit);
				new DegreeCourse(degree, c);
			}
		}
	}

	private static List<String> createUrl(DegreeReq degree) {
		Program p = degree.getProgram();
		ArrayList<String> returnVal = new ArrayList<String>();
		if(p.getIsUG() && degree.getName().contains("Faculty")){
			returnVal.add(String.format(fensCourses, p.getEnterTerm(), p.getName()));
			returnVal.add(String.format(fassCourses, p.getEnterTerm(), p.getName()));
			returnVal.add(String.format(somCourses, p.getEnterTerm(), p.getName()));
		}
		else{
			String isUG = p.getIsUG() ? "UG" : "G";
			returnVal.add(String.format(courseDegreeUrlTemplate, p.getEnterTerm(),
				degree.getHref(), p.getName(), isUG));
		}
		return returnVal; 
	}
	
	public static void main(String [] args)
	{
		try {
			parse(201001,true,"BSCS");
		} catch (IllegalArgumentException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
