package io.scheduler.data;

import io.scheduler.data.handler.DatabaseConnector;

import java.sql.SQLException;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "meetings")
public class Meeting {
	private enum DayofWeek {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, TBA
	}

	public static final String DAY_FIELD_NAME = "day";
	public static final String START_FIELD_NAME = "start";
	public static final String END_FIELD_NAME = "end";
	public static final String SUCLASS_CODE_FIELD_NAME = "crn";
	public static final String PLACE_FIELD_NAME = "place";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = DAY_FIELD_NAME)
	private DayofWeek day;

	@DatabaseField(columnName = START_FIELD_NAME)
	private Date start;

	@DatabaseField(columnName = END_FIELD_NAME)
	private Date end;

	@DatabaseField(columnName = PLACE_FIELD_NAME)
	private String place;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = SUCLASS_CODE_FIELD_NAME, canBeNull = false)
	private SUClass suClass;

	/**
	 * For ormlite
	 */
	Meeting() {
	}

	public Meeting(Date s, Date e, String d, String p, SUClass suClass)
			throws SQLException {
		this.setDay(d);
		this.setStart(s);
		this.setEnd(e);
		this.setSuClass(suClass);
		this.setPlace(p);
		DatabaseConnector.createIfNotExist(this, Meeting.class);
	}

	private DayofWeek stringToDay(String d) {
		switch (d.charAt(0)) {
		case 'M':
			return DayofWeek.MONDAY;
		case 'T':
			return DayofWeek.TUESDAY;
		case 'W':
			return DayofWeek.WEDNESDAY;
		case 'R':
			return DayofWeek.THURSDAY;
		case 'F':
			return DayofWeek.FRIDAY;
		case 'S':
			return DayofWeek.SATURDAY;
		default:
			throw new IllegalArgumentException(d);
		}
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	private void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the day
	 */
	public DayofWeek getDay() {
		return day;
	}

	/**
	 * @param d
	 *            the day to set
	 */
	private void setDay(String d) {
		this.day = stringToDay(d);
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	private void setStart(Date start) {
		this.start = start;
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
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	private void setPlace(String place) {
		this.place = place;
	}

}
