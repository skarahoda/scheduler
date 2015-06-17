package io.scheduler.data.handler;

import io.scheduler.data.Meeting;
import io.scheduler.data.Meeting.DayofWeek;
import io.scheduler.data.SUClass;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class FiltersSUClass {
	public static Collection<SUClass> filterForCoReq(Collection<SUClass> iteratable) {
		return Collections2.filter(iteratable, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return !arg0.hasCoRequisite();
			}
		});
	}

	public static Collection<SUClass> filterForDay(Collection<SUClass> iteratable,
			final DayofWeek day) {
		return Collections2.filter(iteratable, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return arg0.hasMeetingAt(day);
			}
		});
	}

	public static Collection<SUClass> filterForMeeting(Collection<SUClass> iteratable,
			final Meeting meeting) {
		return Collections2.filter(iteratable, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return arg0.intersect(meeting);
			}
		});
	}

}
