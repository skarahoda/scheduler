/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "schedules")
public class Schedule {

	public static final String NAME_FIELD_NAME = "name";
	public static final String CLASSES_FIELD_NAME = "classes";
	public static final String TERM_FIELD_NAME = "term";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = TERM_FIELD_NAME, canBeNull = false, persisterClass = TermPersister.class)
	private Term term;

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
	Schedule(String name, Term term) throws SQLException {
		this.name = name;
		this.term = term;
		DatabaseConnector.createIfNotExist(this, Schedule.class);
	}

	/**
	 * @return the term
	 */
	public Term getTerm() {
		return term;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public List<SUClass> getSUClasses() throws SQLException {
		if (classes == null) {
			this.setClasses();
		}
		List<SUClass> returnVal = new ArrayList<SUClass>();
		for (ScheduleSUClass scheduleClass : classes) {
			returnVal.add(scheduleClass.getSuClass());
		}
		return returnVal;
	}

	public void addSUClass(SUClass suClass) throws SQLException {
		if (suClass == null)
			return;
		if (classes == null) {
			new ScheduleSUClass(suClass, this);
			return;
		}
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

	public static Schedule get(String name, Term term) throws SQLException {
		List<Schedule> schedules = DatabaseConnector.get(Schedule.class);
		for (Schedule schedule : schedules) {
			if (schedule.getName().equals(name)
					&& schedule.getTerm().equals(term))
				return schedule;
		}
		return new Schedule(name, term);
	}

	public static boolean exists(String name, Term term) throws SQLException {
		List<Schedule> schedules = DatabaseConnector.get(Schedule.class);
		for (Schedule schedule : schedules) {
			if (schedule.getName().equals(name)
					&& schedule.getTerm().equals(term))
				return true;
		}
		return false;
	}

	public static List<Schedule> get(Term term) throws SQLException {
		return DatabaseConnector.get(Schedule.class, Schedule.TERM_FIELD_NAME,
				term);
	}

	public static List<Schedule> get() throws SQLException {
		return DatabaseConnector.get(Schedule.class);
	}

	private void setClasses() throws SQLException {
		DatabaseConnector.createTableIfNotExists(ScheduleSUClass.class);
		DatabaseConnector.assignEmptyForeignCollection(this, Schedule.class,
				CLASSES_FIELD_NAME);
	}

	public void removeFromDb() throws SQLException {
		DatabaseConnector.delete(this, Schedule.class);
	}

	@Override
	public String toString() {
		return name + " - " + term;
	}

	public List<Course> getCourses() throws SQLException {
		if (classes == null) {
			this.setClasses();
		}
		List<Course> returnVal = new ArrayList<Course>();
		for (ScheduleSUClass scheduleSUClass : classes) {
			returnVal.add(scheduleSUClass.getCourse());
		}
		return returnVal;
	}

}
