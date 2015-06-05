package io.scheduler.data;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public enum DayofWeek {
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

	private Meeting(Date s, Date e, String d, String p, SUClass suClass) {
		this.setDay(d);
		this.setStart(s);
		this.setEnd(e);
		this.setSuClass(suClass);
		this.setPlace(p);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return day + ", " + df.format(start) + " - " + df.format(end);
	}

	public static Meeting createForDb(Date start, Date end, String day,
			String place, SUClass tempSUClass) throws SQLException {
		Meeting returnVal = new Meeting(start, end, day, place, tempSUClass);
		DatabaseConnector.createIfNotExist(returnVal, Meeting.class);
		return returnVal;
	}

	public static Meeting create(Date start, Date end, String day,
			String place, SUClass tempSUClass) throws SQLException {
		Meeting returnVal = new Meeting(start, end, day, place, tempSUClass);
		return returnVal;
	}
}
