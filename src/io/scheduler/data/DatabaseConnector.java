package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
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

	static <T> int clearTable(Class<T> dataClass) throws SQLException {
		setSource();
		createTableIfNotExists(dataClass);
		return TableUtils.clearTable(DatabaseConnector.source, dataClass);
	}

	static <T> void createTableIfNotExists(Class<T> dataClass)
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

	static <T> List<T> get(Class<T> dataClass) throws SQLException {
		return DatabaseConnector.setDB(dataClass).queryForAll();
	}

	static <T> List<T> get(Class<T> dataClass, String key, Object value)
			throws SQLException {
		return DatabaseConnector.setDB(dataClass).queryForEq(key, value);
	}

	static <T> List<T> get(Class<T> dataClass, Map<String, Object> fieldValues)
			throws SQLException {
		return DatabaseConnector.setDB(dataClass).queryForFieldValues(
				fieldValues);
	}

	static <T> T getFirst(Class<T> dataClass) throws SQLException {
		List<T> list = DatabaseConnector.setDB(dataClass).queryForAll();
		return (list.size() == 0) ? null : list.get(0);
	}

	static <T> void createOrUpdate(T item, Class<T> dataClass)
			throws SQLException {
		Dao<T, Integer> dao = DatabaseConnector.setDB(dataClass);
		dao.createOrUpdate(item);
	}

	static <T> void createIfNotExist(T item, Class<T> dataClass)
			throws SQLException {
		Dao<T, Integer> dao = DatabaseConnector.setDB(dataClass);
		dao.createIfNotExists(item);
	}

	static <T> void delete(T item, Class<T> dataClass) throws SQLException {
		DatabaseConnector.setDB(dataClass).delete(item);
	}

	static <T> void assignEmptyForeignCollection(T item, Class<T> dataClass,
			String fieldName) throws SQLException {
		DatabaseConnector.setDB(dataClass).assignEmptyForeignCollection(item,
				fieldName);
	}

	static <T> T queryForId(Integer id, Class<T> dataClass) throws SQLException {
		return setDB(dataClass).queryForId(id);
	}

	static <T> void delete(Class<T> dataClass, String key, Object value)
			throws SQLException {
		DeleteBuilder<T, Integer> deleteBuilder = setDB(dataClass)
				.deleteBuilder();
		deleteBuilder.where().eq(key, value);
		deleteBuilder.delete();
	}
}
