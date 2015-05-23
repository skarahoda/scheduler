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

	@DatabaseField(columnName = TERM_FIELD_NAME, canBeNull = false)
	private int currentTerm;

	private static User singleton = null;

	/**
	 * For ormlite
	 */
	User() {
	}

	private User(int currentTerm) throws IllegalArgumentException, SQLException {
		DatabaseConnector.clearTable(User.class);
		this.currentTerm = currentTerm;
		DatabaseConnector.createIfNotExist(this, User.class);
	}

	private static void validateTerm(int term) throws IllegalArgumentException {
		if (!isTermValid(term)) {
			throw new IllegalArgumentException("term:(" + term
					+ ") is invalid.");
		}
	}

	private static boolean isTermValid(int term_and_year) {

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		if ((currentYear + 2) * 100 < term_and_year) {
			return false;
		}
		if (200602 > term_and_year) {
			return false;
		}
		if (2 < (term_and_year % 100)) {
			return false;
		}
		return true;
	}

	public static int getCurrentTerm() throws SQLException {
		singleton = DatabaseConnector.getFirst(User.class);
		return singleton == null ? -1 : singleton.currentTerm;
	}

	public static void setCurrentTerm(int currentTerm)
			throws IllegalArgumentException, SQLException {
		if (currentTerm == -1) {
			DatabaseConnector.clearTable(User.class);
			return;
		}
		validateTerm(currentTerm);
		singleton = DatabaseConnector.getFirst(User.class);
		if (singleton == null) {
			singleton = new User(currentTerm);
		} else {
			singleton.currentTerm = currentTerm;
		}
		DatabaseConnector.createOrUpdate(singleton, User.class);
	}

}
