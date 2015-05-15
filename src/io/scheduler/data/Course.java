package io.scheduler.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "courses")
public class Course {

	public static final String CODE_FIELD_NAME = "code";
	public static final String NAME_FIELD_NAME = "name";
	public static final String CREDIT_FIELD_NAME = "credit";
	
	
	@DatabaseField(columnName =  CODE_FIELD_NAME, canBeNull = false, id = true)
	private String code;
	
	@DatabaseField(columnName =  NAME_FIELD_NAME, canBeNull = false)
	private String name;
	
	@DatabaseField(columnName =  CREDIT_FIELD_NAME, canBeNull = false)
	private float credit;

	/**
	 * 
	 */
	public Course() {}

	/**
	 * @param code
	 * @param name
	 * @param credit
	 */
	public Course(String code, String name, float credit) {
		this.setCode(code);
		this.setCredit(credit);
		this.setName(name);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the credit
	 */
	public float getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(float credit) {
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
	    if ((this.code == null) ? (other.code != null) : !this.code.equals(other.code)) {
	        return false;
	    }
	   
	    return true;
	}
}
