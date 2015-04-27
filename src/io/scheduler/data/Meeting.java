package io.scheduler.data;
import java.util.Date;
import java.util.Scanner;

/**
 * 
 * @author skarahoda
 *
 */
public class Meeting {
	private enum DayofWeek { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
	private DayofWeek day;
	private Date start;
	private Date end;
	
	public Meeting(String t, String d){
		switch(d.charAt(0)){
		case 'M': setDay(DayofWeek.MONDAY);		break;
		case 'T': setDay(DayofWeek.TUESDAY);		break;
		case 'W': setDay(DayofWeek.WEDNESDAY);	break;
		case 'R': setDay(DayofWeek.THURSDAY);		break;
		case 'F': setDay(DayofWeek.FRIDAY);		break;
		case 'S': setDay(DayofWeek.SATURDAY);		break;
		default: throw  new IllegalArgumentException(d);
		}
		Scanner time = new Scanner(t);
		System.out.println(time.next());
		System.out.println(time.next());
		time.next();
		System.out.println(time.next());
		System.out.println(time.next());
		time.close();
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the day
	 */
	public DayofWeek getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(DayofWeek day) {
		this.day = day;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	
	
}
