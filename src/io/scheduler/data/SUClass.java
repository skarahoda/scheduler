/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */

@DatabaseTable(tableName = "su_classes")
public class SUClass {
	public static final String CRN_FIELD_NAME = "crn";
	public static final String INSTRUCTOR_FIELD_NAME = "instructor";
	public static final String SECTION_FIELD_NAME = "section";
	public static final String COURSE_CODE_FIELD_NAME = "courseCode";

	@DatabaseField(columnName = CRN_FIELD_NAME, canBeNull = false, id = true)
	private String crn;

	@DatabaseField(columnName = INSTRUCTOR_FIELD_NAME, canBeNull = false, dataType = DataType.LONG_STRING)
	private String instructorName;

	@DatabaseField(columnName = SECTION_FIELD_NAME, canBeNull = false)
	private String section;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COURSE_CODE_FIELD_NAME)
	private Course course;

	@ForeignCollectionField
	private ForeignCollection<Meeting> meetings;

	/**
	 * For ormlite
	 */
	SUClass() {
	}

	/**
	 * 
	 * @param term
	 * @param crn
	 * @param instructor
	 * @param section
	 * @param course
	 * @throws SQLException
	 */
	public SUClass(String crn, String instructor, String section, Course course)
			throws SQLException {
		this.setCrn(crn);
		this.setInstructorName(instructor);
		this.setSection(section);
		this.setCourse(course);
		DatabaseConnector.createIfNotExist(this, SUClass.class);
	}

	public String getCrn() {
		return crn;
	}

	private void setCrn(String crn) {
		this.crn = crn;
	}

	public String getInstructorName() {
		return instructorName;
	}

	private void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	private void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            the course to set
	 */
	private void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the meetings
	 */
	public ForeignCollection<Meeting> getMeetings() {
		return meetings;
	}

	public static List<SUClass> get() throws SQLException {
		return DatabaseConnector.get(SUClass.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return course.getCode() + " - " + section + " - " + instructorName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crn == null) ? 0 : crn.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SUClass other = (SUClass) obj;
		if (crn == null) {
			if (other.crn != null)
				return false;
		} else if (!crn.equals(other.crn))
			return false;
		return true;
	}

	public String getCode() {
		return getCourse().getCode();
	}

}
