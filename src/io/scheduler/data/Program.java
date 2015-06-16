/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "programs")
public class Program {

	public static final String ENTER_TERM_FIELD_NAME = "enterence_term";
	public static final String NAME_FIELD_NAME = "name";
	public static final String IS_UG_FIELD_NAME = "is_ug";
	public static final String REQ_FIELD_NAME = "requirements";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = ENTER_TERM_FIELD_NAME, canBeNull = false, persisterClass = TermPersister.class)
	private Term enterTerm;

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
	private Program(Term enterTerm, String name, boolean isUG)
			throws IllegalArgumentException, SQLException {
		validateTerm(enterTerm);
		this.enterTerm = enterTerm;
		this.name = name;
		this.isUG = isUG;
		DatabaseConnector.createIfNotExist(this, Program.class);
	}

	/**
	 * @return the enterTerm
	 */
	public Term getEnterTerm() {
		return enterTerm;
	}

	private void validateTerm(Term enterTerm) throws IllegalArgumentException {
		if (enterTerm.getYear() < 1999) {
			throw new IllegalArgumentException("enterence year:("
					+ enterTerm.getYear() + ") is less than 1999.");
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the level
	 */
	public boolean getIsUG() {
		return isUG;
	}

	public Collection<DegreeReq> getRequirements() {
		try {
			if (requirements == null)
				setDegreeReqs();
			return requirements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setDegreeReqs() throws SQLException {
		DatabaseConnector.createTableIfNotExists(DegreeCourse.class);
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
		result = prime * result
				+ ((enterTerm == null) ? 0 : enterTerm.hashCode());
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
		if (enterTerm == null) {
			if (other.enterTerm != null)
				return false;
		} else if (!enterTerm.equals(other.enterTerm))
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

	public static Program get(Term term, String name, boolean isUG)
			throws SQLException {
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		fieldValues.put(NAME_FIELD_NAME, name);
		fieldValues.put(ENTER_TERM_FIELD_NAME, term);
		List<Program> list = DatabaseConnector.get(Program.class, fieldValues );
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + " - " + enterTerm;
	}

	public List<DegreeReq> getRequirements(Course course) {
		List<DegreeReq> returnVal = new ArrayList<DegreeReq>();
		if (course == null)
			return returnVal;
		for (DegreeReq degreeReq : requirements) {
			if (degreeReq.getHref() == null || degreeReq.getHref().equals("")) {
				returnVal.add(degreeReq);
			}
			try {
				for (Course degreeCourse : degreeReq.getCourses()) {
					if (degreeCourse.equals(course)) {
						returnVal.add(degreeReq);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnVal;
	}

	public static Program create(Term term, String name, boolean isUG) throws SQLException {
		Program returnVal;
		returnVal = get(term, name, isUG);
		if(returnVal == null){
			returnVal = new Program(term, name, isUG);
		}
		return returnVal;
	}

	public void removeFromDb() {
		try {
			if(requirements == null)
				setDegreeReqs();
			for (DegreeReq degreeReq : requirements) {
				degreeReq.deleteFromDb();
			}
			DatabaseConnector.delete(this, Program.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
