package io.scheduler.data.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnector {
	
	private static Connection getDBConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite:scheduler.sqlite");
	}
	private static void createAllTables(Connection db, Statement stmt) throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS COURSE " +
	                   "(CRN INT PRIMARY KEY NOT NULL," +
	                   " NAME TEXT NOT NULL, " + 
	                   " CODE CHAR(10) NOT NULL, " +
	                   " SECTION CHAR(10) NOT NULL, " +
	                   " TERM CHAR(6) NOT NULL) ";    
		stmt.executeUpdate(sql);
		stmt.close();
	}
	private static void validateStringSize(String string, int length){
		if(string.length() > length) throw new IllegalArgumentException("length of " + string + " is higher than " + Integer.toString(length));
	}
	private static void validateExactSize(String string, int length){
		if(string.length() != length) throw new IllegalArgumentException("length of " + string + " is not equal to " + Integer.toString(length));
	}
	public static void putCourse(int crn, String name, String code, String section, String term) throws SQLException{
		validateStringSize(code, 10);
		validateStringSize(section, 10);
		validateExactSize(term, 6);
		Connection db = getDBConnection();
		Statement stmt = db.createStatement();
		createAllTables(db, stmt);
		String sql = "INSERT OR REPLACE INTO COURSE (CRN,NAME,CODE,SECTION,TERM) " +
                "VALUES ("+ crn + ", '"+ name +"', '"+ code +"', '" + section + "', '" + term + "' );";
		stmt.executeUpdate(sql);
		stmt.close();
		db.close();
	}
	
}
