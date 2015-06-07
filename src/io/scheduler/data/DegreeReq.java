/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "degree_req")
public class DegreeReq {

	public static final String CREDIT_FIELD_NAME = "credit";
	public static final String COURSE_NUM_FIELD_NAME = "course_num";
	public static final String NAME_FIELD_NAME = "name";
	public static final String PROGRAM_NAME_FIELD_NAME = "program_id";
	public static final String HREF_FIELD_NAME = "href";
	public static final String COURSE_FIELD_NAME = "courses";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = CREDIT_FIELD_NAME)
	private int credit;

	@DatabaseField(columnName = COURSE_NUM_FIELD_NAME)
	private int courseNum;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = HREF_FIELD_NAME)
	private String href;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PROGRAM_NAME_FIELD_NAME, canBeNull = false)
	private Program program;

	@ForeignCollectionField(columnName = COURSE_FIELD_NAME)
	private ForeignCollection<DegreeCourse> courses;

	DegreeReq() {
	}

	private DegreeReq(int courseNum, int credit, String name, String href,
			Program program) throws SQLException {
		this.program = program;
		this.credit = credit;
		this.courseNum = courseNum;
		this.name = name;
		this.href = href;
		DatabaseConnector.createIfNotExist(this, DegreeReq.class);
	}

	private DegreeReq(int courseNum, int credit, String name, Program program)
			throws SQLException {
		this.program = program;
		this.credit = credit;
		this.courseNum = courseNum;
		this.name = name;
		DatabaseConnector.createIfNotExist(this, DegreeReq.class);
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @return the courseNum
	 */
	public int getCourseNum() {
		return courseNum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the program
	 */
	public Program getProgram() {
		return program;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	public static DegreeReq get(int courseNum, int credit, String name,
			Program program) throws SQLException {
		for (DegreeReq degreeReq : DatabaseConnector.get(DegreeReq.class)) {
			if (degreeReq.program.equals(program)
					&& degreeReq.name.equals(name)) {
				return degreeReq;
			}
		}
		return new DegreeReq(courseNum, credit, name, program);
	}

	public static DegreeReq get(int courseNum, int credit, String name,
			String href, Program program) throws SQLException {
		for (DegreeReq degreeReq : DatabaseConnector.get(DegreeReq.class)) {
			if (degreeReq.program.equals(program)
					&& degreeReq.name.equals(name)) {
				return degreeReq;
			}
		}
		return new DegreeReq(courseNum, credit, name, href, program);
	}

	public Collection<Course> getCourses() throws SQLException {
		if (courses == null) {
			this.setCourses();
		}
		List<Course> returnVal = new ArrayList<Course>();
		for (DegreeCourse course : courses) {
			returnVal.add(course.getCourse());
		}
		return returnVal;
	}

	private void setCourses() throws SQLException {
		DatabaseConnector.assignEmptyForeignCollection(this, DegreeReq.class,
				COURSE_FIELD_NAME);
	}
}
