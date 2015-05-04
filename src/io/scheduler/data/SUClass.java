/**
 * 
 */
package io.scheduler.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */

@DatabaseTable(tableName = "suClasses")
public class SUClass {

	public static final String TERM_FIELD_NAME = "term";
	public static final String CRN_FIELD_NAME = "crn";
	public static final String INSTRUCTOR_FIELD_NAME = "instructor";
	public static final String SECTION_FIELD_NAME = "section";
	public static final String COURSE_CODE_FIELD_NAME = "courseCode";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName =  TERM_FIELD_NAME, canBeNull = false)
	private String term;
	
	@DatabaseField(columnName =  CRN_FIELD_NAME, canBeNull = false)
	private String crn;
	
	@DatabaseField(columnName =  INSTRUCTOR_FIELD_NAME, canBeNull = false)
	private String instructorName;
	
	@DatabaseField(columnName =  SECTION_FIELD_NAME, canBeNull = false)
	private String section;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COURSE_CODE_FIELD_NAME)
	private Course course;

	/**
	 * 
	 */
	public SUClass() {}

	/**
	 * 
	 * @param term
	 * @param crn
	 * @param instructor
	 * @param section
	 * @param course
	 */
	public SUClass(String term, String crn, String instructor, String section, Course course) {
		this.term = term;
		this.crn = crn;
		this.instructorName = instructor;
		this.section = section;
		this.setCourse(course);
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	
	
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

}
