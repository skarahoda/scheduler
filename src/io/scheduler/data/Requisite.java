/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "requisites")
public class Requisite {

	public enum Operation {
		UNARY, AND, OR
	}

	public static final String OPERATION_FIELD_NAME = "operation";
	public static final String COURSE_CODE_FIELD_NAME = "course_code";
	public static final String LEFT_FIELD_NAME = "left";
	public static final String RIGHT_FIELD_NAME = "right";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = OPERATION_FIELD_NAME)
	private Operation operation;

	@DatabaseField(columnName = COURSE_CODE_FIELD_NAME)
	private String courseCode;

	@DatabaseField(columnName = LEFT_FIELD_NAME)
	private int leftId;

	@DatabaseField(columnName = RIGHT_FIELD_NAME)
	private int rightId;

	Requisite() {
	}

	/**
	 * @param courseName
	 * @throws SQLException
	 */
	public Requisite(String courseCode) throws SQLException {
		this.courseCode = courseCode;
		this.operation = Operation.UNARY;
		DatabaseConnector.createIfNotExist(this, Requisite.class);
	}

	/**
	 * @param left
	 * @param right
	 * @throws SQLException
	 */
	public Requisite(int leftId, int rightId, boolean isAnd)
			throws SQLException, IllegalArgumentException {
		this.leftId = leftId;
		this.rightId = rightId;
		Requisite left = getWithId(leftId);
		Requisite right = getWithId(rightId);
		if (left == null || right == null)
			throw new IllegalArgumentException();
		this.operation = isAnd ? Operation.AND : Operation.OR;
		DatabaseConnector.createIfNotExist(this, Requisite.class);
	}

	private Requisite getWithId(int id) throws SQLException {
		return DatabaseConnector.queryForId(id, Requisite.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		switch (operation) {
		case UNARY:
			return courseCode;
		case AND:
			return "( " + leftId + " and " + rightId + " )";
		case OR:
			return "( " + leftId + " or " + rightId + " )";
		}
		return null;
	}

	public boolean isValid(Collection<Course> courses) {
		Requisite right;
		Requisite left;
		switch (operation) {
		case UNARY:
			for (Course course : courses) {
				if (course.getCode().equals(courseCode)) {
					return true;
				}
			}
			return false;
		case AND:
			try {
				left = getWithId(leftId);
				right = getWithId(rightId);
				if (left == null || right == null)
					return false;
				return left.isValid(courses) && right.isValid(courses);
			} catch (SQLException e) {
				return false;
			}
		case OR:
			try {
				left = getWithId(leftId);
				right = getWithId(rightId);
				if (left == null || right == null)
					return false;
				return left.isValid(courses) || right.isValid(courses);
			} catch (SQLException e) {
				return false;
			}
		}
		return false;

	}

	public int getId() {
		return id;
	}
}
