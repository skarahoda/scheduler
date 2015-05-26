/**
 * 
 */
package io.scheduler.data;

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

	public static final String ENTER_TERM_FIELD_NAME = "enterence_term";
	public static final String NAME_FIELD_NAME = "name";
	public static final String IS_UG_FIELD_NAME = "is_ug";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = ENTER_TERM_FIELD_NAME, canBeNull = false)
	private int enterTerm;

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
	 * @param enterTerm
	 * @param name
	 * @param level
	 * @throws SQLException
	 */
	public Program(int enterTerm, String name, boolean isUG)
			throws IllegalArgumentException, SQLException {
		this.setEnterTerm(enterTerm);
		this.setName(name);
		this.setIsUG(isUG);
		DatabaseConnector.createIfNotExist(this, Program.class);
	}

	/**
	 * @return the enterTerm
	 */
	public int getEnterTerm() {
		return enterTerm;
	}

	/**
	 * @param enterTerm
	 *            the enterTerm to set
	 */
	private void setEnterTerm(int enterTerm) throws IllegalArgumentException {
		validateTerm(enterTerm);
		this.enterTerm = enterTerm;
	}

	private void validateTerm(int enterTerm) throws IllegalArgumentException {
		if (enterTerm < 199901) {
			throw new IllegalArgumentException("enterTerm:(" + enterTerm
					+ ") is less than 1999.");
		}
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if ((currentYear + 2) * 100 < enterTerm) {
			throw new IllegalArgumentException("enterTerm:(" + enterTerm
					+ ") is higher than next year.");
		}
		if (2 < (enterTerm % 100)) {
			throw new IllegalArgumentException("enterTerm:(" + enterTerm
					+ ") last two digit is not valid.");
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
