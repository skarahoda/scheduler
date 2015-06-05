package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "taken_courses")
public class TakenCourse {
	private static final String COURSE_CODE_FIELD_NAME = "code";
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COURSE_CODE_FIELD_NAME, canBeNull = false)
	private Course course;

	@DatabaseField(generatedId = true)
	private int id;

	/**
	 * for ormlite
	 */
	TakenCourse() {
	}

	/**
	 * @param course
	 * @throws SQLException
	 */
	private TakenCourse(Course course) throws SQLException {
		this.course = course;
		DatabaseConnector.createOrUpdate(this, TakenCourse.class);
	}

	public static List<Course> getAll() throws SQLException {
		List<Course> returnVal = new ArrayList<Course>();
		for (TakenCourse course : DatabaseConnector.get(TakenCourse.class)) {
			returnVal.add(course.course);
		}
		return returnVal;
	}

	public static void addCourse(Course addedCourse) throws SQLException {
		if (addedCourse == null)
			return;
		for (TakenCourse course : DatabaseConnector.get(TakenCourse.class)) {
			if (course.course.equals(addedCourse)) {
				return;
			}
		}
		new TakenCourse(addedCourse);
	}

	public static void deleteCourse(Course deletedCourse) throws SQLException {
		for (TakenCourse course : DatabaseConnector.get(TakenCourse.class)) {
			if (course.course.equals(deletedCourse)) {
				DatabaseConnector.delete(course, TakenCourse.class);
			}
		}
	}

}
