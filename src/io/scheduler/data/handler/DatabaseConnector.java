package io.scheduler.data.handler;

import io.scheduler.data.SUClass;
import io.scheduler.data.User;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseConnector {
	
	private final static String DATABASE_URL = "jdbc:h2:file:./scheduler";
	
	public static User getUser() throws SQLException{
		List<User> users = setDB(User.class).queryForAll();
		return (users.size() == 0) ? null : users.get(0);
	}
	
	public static void setSUClasses(List<SUClass> classes) throws SQLException{
		Dao<SUClass,Integer> daoSUClass = setDB(SUClass.class);
		QueryBuilder<SUClass, Integer> qb = daoSUClass.queryBuilder();
		qb.where().eq(SUClass.TERM_FIELD_NAME, classes.get(0).getTerm());
		PreparedQuery<SUClass> prepared = qb.prepare();
		List<SUClass> deletedClasses = daoSUClass.query(prepared);
		daoSUClass.delete(deletedClasses);
		for(SUClass suClass: classes){
			daoSUClass.create(suClass);
		}
	}
	
	public static List<SUClass> getSUClasses() throws SQLException{
		List<SUClass> classes = setDB(SUClass.class).queryForAll();
		return classes;
	}
	
	public static void setUser(User newUser) throws SQLException{
		setDB(User.class).createOrUpdate(newUser);
	}

	private static <T> Dao<T, Integer> setDB(Class<T> class1) throws SQLException {
		
		ConnectionSource source = new JdbcConnectionSource(DATABASE_URL);
		Dao<T, Integer> returnVal = DaoManager.createDao(source, class1);
		TableUtils.createTableIfNotExists(source, class1);
		return returnVal;
	}
}
