package io.scheduler.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {
	
	public static final String TERM_FIELD_NAME = "currentTerm";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName =  TERM_FIELD_NAME, canBeNull = false)
	private String currentTerm;
	
	public User(){}
	
	public User(String currentTerm) {
		this.currentTerm = currentTerm;
	}
	
	public String getCurrentTerm() {
		return currentTerm;
	}

	public void setCurrentTerm(String currentTerm) {
		this.currentTerm = currentTerm;
	}

}
