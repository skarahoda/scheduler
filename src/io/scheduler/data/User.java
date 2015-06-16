package io.scheduler.data;

import io.scheduler.data.Term.TermOfYear;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

	public static final String TERM_FIELD_NAME = "currentTerm";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = TERM_FIELD_NAME, canBeNull = false, persisterClass = TermPersister.class)
	private Term currentTerm;

	private static User singleton = null;

	/**
	 * For ormlite
	 */
	User() {
	}

	private User(Term currentTerm) throws IllegalArgumentException,
			SQLException {
		DatabaseConnector.clearTable(User.class);
		this.currentTerm = currentTerm;
		DatabaseConnector.createIfNotExist(this, User.class);
	}

	private static void validateTerm(Term term) throws IllegalArgumentException {
		if ((term.getTerm() == TermOfYear.FALL && term.getYear() == 2006)
				|| term.getYear() < 2006)
			throw new IllegalArgumentException("Term: " + term
					+ " is too old to get schedule");
	}

	public static Term getCurrentTerm() {
		try {
			singleton = DatabaseConnector.getFirst(User.class);
		} catch (SQLException e) {
			singleton = null;
		}
		return singleton == null ? null : singleton.currentTerm;
	}

	public static boolean canGet() {
		try {
			singleton = DatabaseConnector.getFirst(User.class);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public static void setCurrentTerm(Term currentTerm)
			throws IllegalArgumentException, SQLException {
		if (currentTerm == null) {
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
