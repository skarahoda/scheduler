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
	public enum DayOfWeek {
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
	private DayOfWeek day;

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

	private Meeting(Date s, Date e, DayOfWeek d, String p, SUClass suClass) {
		this.day = d;
		this.start = s;
		this.end = e;
		this.suClass = suClass;
		this.place = p;
	}

	public static DayOfWeek stringToDay(String d) {
		switch (d.charAt(0)) {
		case 'M':
			return DayOfWeek.MONDAY;
		case 'T':
			return DayOfWeek.TUESDAY;
		case 'W':
			return DayOfWeek.WEDNESDAY;
		case 'R':
			return DayOfWeek.THURSDAY;
		case 'F':
			return DayOfWeek.FRIDAY;
		case 'S':
			return DayOfWeek.SATURDAY;
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
	 * @return the day
	 */
	public DayOfWeek getDay() {
		return day;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @return the suClass
	 */
	public SUClass getSuClass() {
		return suClass;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
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

	public static Meeting createForDb(Date start, Date end, DayOfWeek day,
			String place, SUClass tempSUClass) throws SQLException {
		Meeting returnVal = new Meeting(start, end, day, place, tempSUClass);
		DatabaseConnector.createOrUpdate(returnVal, Meeting.class);
		return returnVal;
	}

	public static Meeting create(Date start, Date end, DayOfWeek day,
			String place, SUClass tempSUClass) {
		Meeting returnVal = new Meeting(start, end, day, place, tempSUClass);
		return returnVal;
	}

	public boolean intersect(Meeting other) {
		return this.day == other.day
				&& !(other.end.before(this.start) || other.start
						.after(this.end));
	}
}
