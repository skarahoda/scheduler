/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
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

	DegreeReq() {
	}

	public DegreeReq(int courseNum, int credit, String name, String href,
			Program program) throws SQLException {
		this.setProgram(program);
		this.setCredit(credit);
		this.setCourseNum(courseNum);
		this.setName(name);
		this.setHref(href);
		DatabaseConnector.createIfNotExist(this, DegreeReq.class);
	}

	public DegreeReq(int courseNum, int credit, String name, Program program)
			throws SQLException {
		this.setProgram(program);
		this.setCredit(credit);
		this.setCourseNum(courseNum);
		this.setName(name);
		this.setHref(href);
		DatabaseConnector.createIfNotExist(this, DegreeReq.class);
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit
	 *            the credit to set
	 */
	private void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return the courseNum
	 */
	public int getCourseNum() {
		return courseNum;
	}

	/**
	 * @param courseNum
	 *            the courseNum to set
	 */
	private void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the program
	 */
	public Program getProgram() {
		return program;
	}

	/**
	 * @param program
	 *            the program to set
	 */
	private void setProgram(Program program) {
		this.program = program;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	private void setHref(String href) {
		this.href = href;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
