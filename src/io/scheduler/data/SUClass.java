/**
 * 
 */
package io.scheduler.data;

import io.scheduler.data.Meeting.DayofWeek;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static final String COURSE_CODE_FIELD_NAME = "course_code";
	private static final String TERM_FIELD_NAME = "term";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = CRN_FIELD_NAME, canBeNull = false)
	private String crn;

	@DatabaseField(columnName = INSTRUCTOR_FIELD_NAME, canBeNull = false, dataType = DataType.LONG_STRING)
	private String instructorName;

	@DatabaseField(columnName = SECTION_FIELD_NAME, canBeNull = false)
	private String section;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COURSE_CODE_FIELD_NAME)
	private Course course;

	@DatabaseField(columnName = TERM_FIELD_NAME, canBeNull = false, persisterClass = TermPersister.class)
	private Term term;

	@ForeignCollectionField(eager = true)
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
	private SUClass(String crn, String instructor, String section,
			Course course, Term term) {
		this.crn = crn;
		this.instructorName = instructor;
		this.section = section;
		this.course = course;
		this.term = term;
	}

	public String getCrn() {
		return crn;
	}

	public String getInstructorName() {
		return instructorName;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @return the meetings
	 */
	public Collection<Meeting> getMeetings() {
		return meetings;
	}

	public static List<SUClass> get(Term term) throws SQLException {
		return DatabaseConnector.get(SUClass.class, SUClass.TERM_FIELD_NAME,
				term);
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

	public boolean intersect(Meeting other) {
		for (Meeting meeting : meetings) {
			if (meeting.intersect(other)) {
				return true;
			}
		}
		return false;
	}

	public static SUClass create(String crn, String instructor, String section,
			Course course, Term term) throws SQLException {
		SUClass returnVal;
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put(TERM_FIELD_NAME, term);
		fields.put(CRN_FIELD_NAME, crn);
		List<SUClass> results = DatabaseConnector.get(SUClass.class, fields);
		if (results != null && !results.isEmpty()) {
			returnVal = results.get(0);
			DatabaseConnector.delete(Meeting.class,
					Meeting.SUCLASS_CODE_FIELD_NAME, returnVal);
			return returnVal;
		}
		returnVal = new SUClass(crn, instructor, section, course, term);
		DatabaseConnector.createIfNotExist(returnVal, SUClass.class);
		return returnVal;
	}

	/**
	 * @return
	 * @throws SQLException
	 * @see io.scheduler.data.Course#hasPreRequisiteRestriction()
	 */
	public boolean hasPreRequisiteRestriction() throws SQLException {
		return course.hasPreRequisiteRestriction();
	}

	/**
	 * @param s
	 * @return
	 * @throws SQLException
	 * @see io.scheduler.data.Course#hasCoRequisiteRestriction(io.scheduler.data.Schedule)
	 */
	public boolean hasCoRequisiteRestriction(Schedule s) throws SQLException {
		return course.hasCoRequisiteRestriction(s);
	}

	/**
	 * @return
	 * @see io.scheduler.data.Course#hasCoRequisite()
	 */
	public boolean hasCoRequisite() {
		return course.hasCoRequisite();
	}

	public boolean hasMeetingAt(DayofWeek day) {
		if (day == DayofWeek.TBA && (meetings == null || meetings.isEmpty())) {
			return true;
		}
		for (Meeting meeting : meetings) {
			if (meeting.getDay() == day) {
				return true;
			}
		}
		return false;
	}

}
