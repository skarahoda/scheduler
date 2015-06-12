package io.scheduler.data;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "courses")
public class Course {

	public static final String CODE_FIELD_NAME = "code";
	public static final String NAME_FIELD_NAME = "name";
	public static final String CREDIT_FIELD_NAME = "credit";
	public static final String CHECKED_FIELD_NAME = "checked_for_req";
	public static final String PREREQ_FIELD_NAME = "prereq";
	public static final String COREQ_FIELD_NAME = "coreq";

	@DatabaseField(columnName = CODE_FIELD_NAME, canBeNull = false, id = true)
	private String code;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = CREDIT_FIELD_NAME, canBeNull = false)
	private float credit;

	@DatabaseField(columnName = CHECKED_FIELD_NAME)
	private boolean isCheckedForReq;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PREREQ_FIELD_NAME)
	private Requisite preReq;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COREQ_FIELD_NAME)
	private Requisite coReq;

	private static HashMap<String, Course> courseMap = null;

	/**
	 * For ormlite
	 */
	Course() {
	}

	/**
	 * @param code
	 * @param name
	 * @param credit
	 * @throws SQLException
	 */
	private Course(String code, String name, float credit) {
		this.code = code;
		this.credit = credit;
		this.name = name;
		this.isCheckedForReq = false;
	}

	public static Course create(String code, String name, float credit)
			throws SQLException {
		if (courseMap == null)
			createHash();
		Course c = courseMap.get(code);
		if (c == null) {
			c = new Course(code, name, credit);
			courseMap.put(code, c);
			DatabaseConnector.createIfNotExist(c, Course.class);
		}
		return c;
	}

	public static Course get(String code) throws SQLException {
		if (courseMap == null)
			createHash();
		Course c = courseMap.get(code);
		return c;
	}

	private static void createHash() throws SQLException {
		courseMap = new HashMap<String, Course>();
		List<Course> courses = DatabaseConnector.get(Course.class);
		for (Course course : courses) {
			courseMap.put(course.getCode(), course);
		}
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the credit
	 */
	public float getCredit() {
		return credit;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Course other = (Course) obj;
		if ((this.code == null) ? (other.code != null) : !this.code
				.equals(other.code)) {
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return code + " - " + name;
	}

	public static List<Course> getAll() throws SQLException {
		return DatabaseConnector.get(Course.class);
	}

	/**
	 * @param preReq
	 *            the preReq to set
	 * @throws SQLException
	 */
	public void setReqs(Requisite preReq, Requisite coReq) throws SQLException {
		this.preReq = preReq;
		this.coReq = coReq;
		this.isCheckedForReq = true;
		DatabaseConnector.createOrUpdate(this, Course.class);
	}

	/**
	 * @return the isCheckedForReq
	 */
	public boolean isCheckedForReq() {
		return isCheckedForReq;
	}

	public boolean hasPreRequisiteRestriction() throws SQLException {
		if (preReq != null) {
			if (preReq.isValid(TakenCourse.getAll()) == false) {
				return false;
			}
		}
		return true;
	}

	public boolean hasCoRequisiteRestriction(Schedule s) throws SQLException {
		if (coReq != null) {
			if (coReq.isValid(s.getCourses()) == false) {
				return false;
			}
		}
		return true;
	}

	public boolean hasCoRequisite() {
		return coReq != null;
	}
}
