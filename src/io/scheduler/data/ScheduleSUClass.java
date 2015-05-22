package io.scheduler.data;

import io.scheduler.data.handler.DatabaseConnector;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "schedule_su_class")
public class ScheduleSUClass {

	public final static String CRN_FIELD_NAME = "crn";
	public final static String SCHEDULE_FIELD_NAME = "schedule";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = CRN_FIELD_NAME, canBeNull = false)
	private SUClass suClass;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = SCHEDULE_FIELD_NAME, canBeNull = false)
	private Schedule schedule;

	ScheduleSUClass() {
	}

	ScheduleSUClass(SUClass suClass, Schedule schedule) throws SQLException {
		this.setSuClass(suClass);
		this.setSchedule(schedule);
		DatabaseConnector.createIfNotExist(this, ScheduleSUClass.class);
	}

	/**
	 * @return the suClass
	 */
	public SUClass getSuClass() {
		return suClass;
	}

	/**
	 * @param suClass
	 *            the suClass to set
	 */
	private void setSuClass(SUClass suClass) {
		this.suClass = suClass;
	}

	/**
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule
	 *            the schedule to set
	 */
	private void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
