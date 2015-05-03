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

	public static final String TERM_FIELD_NAME = "TERM";
	public static final String CRN_FIELD_NAME = "crn";
	public static final String INSTRUCTOR_FIELD_NAME = "instructor";
	public static final String SECTION_FIELD_NAME = "section";
	
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
	 */
	public SUClass(String term, String crn, String instructor, String section) {
		this.term = term;
		this.crn = crn;
		this.instructorName = instructor;
		this.section = section;
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

}
