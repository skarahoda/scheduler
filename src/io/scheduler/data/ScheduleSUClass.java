package io.scheduler.data;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "schedule_su_classes")
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
		this.suClass = suClass;
		this.schedule = schedule;
		DatabaseConnector.createIfNotExist(this, ScheduleSUClass.class);
	}

	/**
	 * @return the suClass
	 */
	public SUClass getSuClass() {
		return suClass;
	}

	/**
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	public Course getCourse() {
		return suClass.getCourse();
	}
}
