/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
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
	public static final String REQ_FIELD_NAME = "requirements";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = ENTER_TERM_FIELD_NAME, canBeNull = false)
	private int enterTerm;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = IS_UG_FIELD_NAME, canBeNull = false)
	private boolean isUG;

	@ForeignCollectionField(columnName = REQ_FIELD_NAME)
	private ForeignCollection<DegreeReq> requirements;

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
	private Program(int enterTerm, String name, boolean isUG)
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

	public Object[] getRequirements() {
		try {
			if (requirements == null)
				setDegreeReqs();
			return requirements.toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setDegreeReqs() throws SQLException {
		DatabaseConnector.assignEmptyForeignCollection(this, Program.class,
				REQ_FIELD_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + enterTerm;
		result = prime * result + (isUG ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Program other = (Program) obj;
		if (enterTerm != other.enterTerm)
			return false;
		if (isUG != other.isUG)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static List<Program> getAll() throws SQLException {
		return DatabaseConnector.get(Program.class);
	}

	public static Program get(int term, String name, boolean isUG)
			throws SQLException {
		for (Program program : getAll()) {
			if (program.enterTerm == term && program.name.equals(name)) {
				return program;
			}
		}
		return new Program(term, name, isUG);
	}

	public void removeFromDB() throws SQLException {
		DatabaseConnector.delete(this, Program.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	public DegreeReq getDegreeReq(Course course) throws SQLException {
		for (DegreeReq degreeReq : requirements) {
			for (Course degreeCourse : degreeReq.getCourses()) {
				if (degreeCourse.equals(course)) {
					return degreeReq;
				}
			}
		}
		return null;
	}
}
