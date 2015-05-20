package io.scheduler.data;

import io.scheduler.data.handler.DatabaseConnector;

import java.sql.SQLException;
import java.util.Calendar;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {
	
	public static final String TERM_FIELD_NAME = "currentTerm";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName =  TERM_FIELD_NAME, canBeNull = false)
	private String currentTerm;
	
	/**
	 * For ormlite
	 */
	User(){}
	
	public User(String currentTerm) throws IllegalArgumentException, SQLException{
		validateTerm(currentTerm);
	    this.currentTerm = currentTerm;
	    DatabaseConnector.createIfNotExist(this, User.class);
	}
	
	private void validateTerm(String term) throws IllegalArgumentException{
		if(!isTermValid(term)){
			throw new IllegalArgumentException("term:("+ term +") is invalid.");
		}
	}
	
	private boolean isTermValid(String term_and_year){
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if(term_and_year.length() != 6){
			return false;
		}
		int year;
		try {
			year = Integer.parseInt(term_and_year.substring(0, 4));
		}
		catch(Exception e){
			return false;
		}
		if( year > currentYear+1    ){
			return false;
		}
		
		
		
		int term;
		try{
		term = Integer.parseInt(term_and_year.substring(4));
		}
		catch(Exception e){
			return false;
		}
		if(term < 1 && term >4){
			return false;
		}
		
		return true;
	}
	
	public String getCurrentTerm() {
		return currentTerm;
	}

	public void setCurrentTerm(String currentTerm) throws IllegalArgumentException, SQLException{
		validateTerm(currentTerm);
		this.currentTerm = currentTerm;
		DatabaseConnector.createOrUpdate(this, User.class);
	}

}
