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

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@ForeignCollectionField
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
	Schedule(String name) throws SQLException {
		this.setName(name);
		DatabaseConnector.createIfNotExist(this, Schedule.class);
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

	public Collection<SUClass> getSUClasses() {
		if (classes == null) {
			return null;
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

	public static Schedule get(String name) throws SQLException {
		List<Schedule> schedules = DatabaseConnector.get(Schedule.class);
		for (Schedule schedule : schedules) {
			if (schedule.getName().equals(name))
				return schedule;
		}
		return new Schedule(name);
	}

	public static boolean exists(String name) throws SQLException {
		List<Schedule> schedules = DatabaseConnector.get(Schedule.class);
		for (Schedule schedule : schedules) {
			if (schedule.getName().equals(name))
				return true;
		}
		return false;
	}

	public static List<Schedule> get() throws SQLException {
		return DatabaseConnector.get(Schedule.class);
	}

}
