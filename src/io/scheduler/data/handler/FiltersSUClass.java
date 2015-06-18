package io.scheduler.data.handler;

import io.scheduler.data.Course;
import io.scheduler.data.Meeting;
import io.scheduler.data.Meeting.DayOfWeek;
import io.scheduler.data.SUClass;
import io.scheduler.data.SUClass.ComparisonOperator;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class FiltersSUClass {
	public static Collection<SUClass> filterForCoReq(Collection<SUClass> classes) {
		return Collections2.filter(classes, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return !arg0.hasCoRequisite();
			}
		});
	}

	public static Collection<SUClass> filterForDay(Collection<SUClass> classes,
			final DayOfWeek day) {
		return Collections2.filter(classes, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return arg0.hasMeetingAt(day);
			}
		});
	}

	public static Collection<SUClass> filterForMeeting(
			Collection<SUClass> classes, final Meeting meeting) {
		return Collections2.filter(classes, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return arg0.intersect(meeting);
			}
		});
	}

	public static Collection<SUClass> filterExcept(Collection<SUClass> classes,
			final Collection<Course> courses) {
		return Collections2.filter(classes, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return !courses.contains(arg0.getCourse());
			}
		});
	}

	public static Collection<SUClass> filterTime(Collection<SUClass> classes,
			final ComparisonOperator op, final Date time) {
		return Collections2.filter(classes, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return arg0.compare(op, time);
			}
		});
	}

	public static Collection<SUClass> filterPreReq(Collection<SUClass> classes,
			final Collection<Course> courses) {
		return Collections2.filter(classes, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				try {
					return arg0.hasPreRequisiteRestriction(courses);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return true;
				}
			}
		});
	}
}
