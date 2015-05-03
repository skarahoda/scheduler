package io.scheduler.data.handler;

import io.scheduler.data.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DatabaseConnector {
	
	private final static String DATABASE_URL = "jdbc:h2:file:./scheduler";
	
	public static User getUser() throws SQLException{
		List<User> users = setDB(User.class).queryForAll();
		return (users.size() == 0) ? null : users.get(0);
	}
	
	public static void setUser(User newUser) throws SQLException{
		setDB(User.class).createOrUpdate(newUser);
	}

	private static <T> Dao<T, Integer> setDB(Class<T> class1) throws SQLException {
		
		ConnectionSource source = new JdbcConnectionSource(DATABASE_URL);
		Dao<T, Integer> returnVal = DaoManager.createDao(source, class1);
		TableUtils.createTableIfNotExists(source, class1);
		return returnVal;
		// TODO Auto-generated method stub
		
	}
}
