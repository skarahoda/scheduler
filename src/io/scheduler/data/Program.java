/**
 * 
 */
package io.scheduler.data;

import io.scheduler.data.handler.DatabaseConnector;

import java.sql.SQLException;
import java.util.Calendar;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "program")
public class Program {

	public static final String ENTER_YEAR_FIELD_NAME = "enterence_year";
	public static final String NAME_FIELD_NAME = "name";
	public static final String IS_UG_FIELD_NAME = "is_ug";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = ENTER_YEAR_FIELD_NAME, canBeNull = false)
	private int enterYear;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = IS_UG_FIELD_NAME, canBeNull = false)
	private boolean isUG;

	/**
	 * for ormlite
	 */
	Program() {
	}

	/**
	 * @param enterYear
	 * @param name
	 * @param level
	 * @throws SQLException
	 */
	Program(int enterYear, String name, boolean isUG)
			throws IllegalArgumentException, SQLException {
		this.setEnterYear(enterYear);
		this.setName(name);
		this.setIsUG(isUG);
		DatabaseConnector.createIfNotExist(this, Program.class);
	}

	/**
	 * @return the enterYear
	 */
	public int getEnterYear() {
		return enterYear;
	}

	/**
	 * @param enterYear
	 *            the enterYear to set
	 */
	private void setEnterYear(int enterYear) throws IllegalArgumentException {
		validateYear(enterYear);
		this.enterYear = enterYear;
	}

	private void validateYear(int enterYear) throws IllegalArgumentException {
		if (enterYear < 1999) {
			throw new IllegalArgumentException("enterYear:(" + enterYear
					+ ") is less than 1999.");
		}
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (currentYear + 1 < enterYear) {
			throw new IllegalArgumentException("enterYear:(" + enterYear
					+ ") is higher than next year.");
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the level
	 */
	public boolean getIsUG() {
		return isUG;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	private void setIsUG(boolean isUG) {
		this.isUG = isUG;
	}

}
