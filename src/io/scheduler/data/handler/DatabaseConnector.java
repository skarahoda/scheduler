package io.scheduler.data.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseConnector {

	private final static String DATABASE_URL = "jdbc:h2:file:./scheduler;mv_store=false";
	private static ConnectionSource source = null;
	private static List<Class<?>> dataList;

	private static <T> Dao<T, Integer> setDB(Class<T> dataClass)
			throws SQLException {
		setSource();
		Dao<T, Integer> returnVal = DaoManager.createDao(source, dataClass);
		createTableIfNotExists(dataClass);
		return returnVal;
	}

	private static void setSource() throws SQLException {
		if (DatabaseConnector.source == null)
			DatabaseConnector.source = new JdbcConnectionSource(DATABASE_URL);
	}

	public static <T> int clearTable(Class<T> dataClass) throws SQLException {
		setSource();
		createTableIfNotExists(dataClass);
		return TableUtils.clearTable(DatabaseConnector.source, dataClass);
	}

	private static <T> void createTableIfNotExists(Class<T> dataClass)
			throws SQLException {
		setSource();
		if (dataList == null) {
			dataList = new ArrayList<Class<?>>();
		}
		if (!dataList.contains(dataClass)) {
			TableUtils.createTableIfNotExists(DatabaseConnector.source,
					dataClass);
			dataList.add(dataClass);
		}
	}

	public static <T> Collection<T> get(Class<T> dataClass) throws SQLException {
		Collection<T> returnVal = DatabaseConnector.setDB(dataClass)
				.queryForAll();
		return returnVal;
	}

	public static <T> T getFirst(Class<T> dataClass) throws SQLException {
		List<T> list = DatabaseConnector.setDB(dataClass).queryForAll();
		return (list.size() == 0) ? null : list.get(0);
	}

	public static <T> void createIfNotExist(Collection<T> list,
			Class<T> dataClass) throws SQLException {
		Dao<T, Integer> dao = DatabaseConnector.setDB(dataClass);
		for (T item : list) {
			dao.createIfNotExists(item);
		}
	}

	public static <T> void create(Collection<T> list, Class<T> dataClass)
			throws SQLException {
		Dao<T, Integer> dao = DatabaseConnector.setDB(dataClass);
		for (T item : list) {
			dao.create(item);
		}
	}

	public static <T> void createOrUpdate(T item, Class<T> dataClass)
			throws SQLException {
		Dao<T, Integer> dao = DatabaseConnector.setDB(dataClass);
		dao.createOrUpdate(item);
	}

	public static <T> void createIfNotExist(T item, Class<T> dataClass)
			throws SQLException {
		Dao<T, Integer> dao = DatabaseConnector.setDB(dataClass);
		dao.createIfNotExists(item);
	}
}
