/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "schedule")
public class Schedule {

	public static final String NAME_FIELD_NAME = "name";
	public static final String CLASSES_FIELD_NAME = "classes";
	public static final String TERM_FIELD_NAME = "term";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = TERM_FIELD_NAME, canBeNull = false)
	private int term;

	@ForeignCollectionField(columnName = CLASSES_FIELD_NAME)
	private ForeignCollection<ScheduleSUClass> classes;

	/**
	 * For ormlite
	 */
	Schedule() {
	}

	/**
	 * 
	 * @param name
	 * @throws SQLException
	 */
	Schedule(String name, int term) throws SQLException {
		this.setName(name);
		this.setTerm(term);
		DatabaseConnector.createIfNotExist(this, Schedule.class);
	}

	/**
	 * @return the term
	 */
	public int getTerm() {
		return term;
	}

	/**
	 * @param term
	 *            the term to set
	 */
	private void setTerm(int term) {
		this.term = term;
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

	public Collection<SUClass> getSUClasses() throws SQLException {
		if (classes == null) {
			this.setClasses();
		}
		List<SUClass> returnVal = new ArrayList<SUClass>();
		Iterator<ScheduleSUClass> i = classes.iterator();
		while (i.hasNext()) {
			SUClass suClass = i.next().getSuClass();
			returnVal.add(suClass);
		}
		return returnVal;
	}

	public void addSUClass(SUClass suClass) throws SQLException {
		if (suClass == null)
			return;
		for (ScheduleSUClass scheduleSUClass : classes) {
			if (scheduleSUClass.getSuClass().equals(suClass)) {
				return;
			}
		}
		new ScheduleSUClass(suClass, this);
	}

	public void deleteSUClass(SUClass suClass) throws SQLException {
		if (suClass == null)
			return;
		for (ScheduleSUClass scheduleSUClass : classes) {
			if (scheduleSUClass.getSuClass().equals(suClass)) {
				DatabaseConnector
						.delete(scheduleSUClass, ScheduleSUClass.class);
			}
		}
	}

	public static Schedule get(String name, int term) throws SQLException {
		List<Schedule> schedules = DatabaseConnector.get(Schedule.class);
		for (Schedule schedule : schedules) {
			if (schedule.getName().equals(name))
				return schedule;
		}
		return new Schedule(name, term);
	}

	public static boolean exists(String name, int term) throws SQLException {
		List<Schedule> schedules = DatabaseConnector.get(Schedule.class);
		for (Schedule schedule : schedules) {
			if (schedule.getName().equals(name) && schedule.getTerm() == term)
				return true;
		}
		return false;
	}

	public static List<Schedule> get(int term) throws SQLException {
		return DatabaseConnector.get(Schedule.class, Schedule.TERM_FIELD_NAME,
				term);
	}

	private void setClasses() throws SQLException {
		DatabaseConnector.assignEmptyForeignCollection(this, Schedule.class,
				CLASSES_FIELD_NAME);
	}

	public void removeFromDb() throws SQLException {
		DatabaseConnector.delete(this, Schedule.class);
	}

	@Override
	public String toString() {
		return name;
	}

}
