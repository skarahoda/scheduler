package io.scheduler.data;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "degree_courses")
public class DegreeCourse {

	public static final String DEGREE_FIELD_NAME = "degree";
	public static final String COURSE_FIELD_NAME = "course";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = DEGREE_FIELD_NAME, canBeNull = false)
	private DegreeReq degree;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COURSE_FIELD_NAME, canBeNull = false)
	private Course course;

	/**
	 * For ormlite
	 */
	DegreeCourse() {
	}

	/**
	 * @param degree
	 * @param course
	 * @throws SQLException
	 */
	public DegreeCourse(DegreeReq degree, Course course) throws SQLException {
		this.degree = degree;
		this.course = course;
		DatabaseConnector.createIfNotExist(this, DegreeCourse.class);
	}

	/**
	 * @return the degree
	 */
	public DegreeReq getDegree() {
		return degree;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	public void deleteFromDb() {
		try {
			DatabaseConnector.delete(this, DegreeCourse.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
