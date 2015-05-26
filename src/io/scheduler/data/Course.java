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

	@DatabaseField(columnName = CODE_FIELD_NAME, canBeNull = false, id = true)
	private String code;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = CREDIT_FIELD_NAME, canBeNull = false)
	private float credit;

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
		this.setCode(code);
		this.setCredit(credit);
		this.setName(name);
	}

	public static Course get(String code, String name, float credit)
			throws SQLException {
		if (courseMap == null)
			createHash();
		Course c = courseMap.get(name);
		if (c == null) {
			c = new Course(code, name, credit);
			courseMap.put(code, c);
			DatabaseConnector.createIfNotExist(c, Course.class);
		}
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
	 * @param code
	 *            the code to set
	 */
	private void setCode(String code) {
		this.code = code;
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
	 * @return the credit
	 */
	public float getCredit() {
		return credit;
	}

	/**
	 * @param credit
	 *            the credit to set
	 */
	private void setCredit(float credit) {
		this.credit = credit;
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
}
