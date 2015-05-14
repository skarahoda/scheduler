package io.scheduler.data.handler;

import io.scheduler.data.Course;
import io.scheduler.data.Meeting;
import io.scheduler.data.SUClass;
import io.scheduler.data.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseConnector {
	
	private final static String DATABASE_URL = "jdbc:h2:file:./scheduler;mv_store=false";
	
	public static User getUser() throws SQLException{
		List<User> users = setDB(User.class).queryForAll();
		return (users.size() == 0) ? null : users.get(0);
		
	}
	
	public static void setSUClasses(List<SUClass> classes) throws SQLException{
		Dao<SUClass,Integer> daoSUClass = setDB(SUClass.class);
		for(SUClass suClass: classes){
			daoSUClass.create(suClass);
		}
	}
	
	public static Collection<SUClass> getSUClasses() throws SQLException{
		Collection<SUClass> classes = setDB(SUClass.class).queryForAll();
		return classes;
	}

	
	public static void setUser(User newUser) throws SQLException{
		setDB(User.class).createOrUpdate(newUser);
	}

	private static <T> Dao<T, Integer> setDB(Class<T> dataClass) throws SQLException {
		
		ConnectionSource source = new JdbcConnectionSource(DATABASE_URL);
		Dao<T, Integer> returnVal = DaoManager.createDao(source, dataClass);
		TableUtils.createTableIfNotExists(source, dataClass);
		return returnVal;
	}

	public static void setCourses(Collection<Course> courses) throws SQLException {
		Dao<Course, Integer> daoCourses = setDB(Course.class);
		for(Course course: courses){
			daoCourses.createIfNotExists(course);
		}
		
	}	
	
	public static Collection<Course> getCourses() throws SQLException {
		Collection<Course> courses = setDB(Course.class).queryForAll();
		return courses;
	}

	public static void setMeetings(Collection<Meeting> meetings) throws SQLException {
		Dao<Meeting, Integer> daoCourses = setDB(Meeting.class);
		for(Meeting meeting: meetings){
			daoCourses.createIfNotExists(meeting);
		}
		
	}	
	
	public static Collection<Meeting> getMeetings() throws SQLException {
		Collection<Meeting> meetings = setDB(Meeting.class).queryForAll();
		return meetings;
	}
	
	public static <T> int clearTable(Class<T> dataClass) throws SQLException{
		ConnectionSource source = new JdbcConnectionSource(DATABASE_URL);
		TableUtils.createTableIfNotExists(source, dataClass);
		return TableUtils.clearTable(source, dataClass);
	}
}
