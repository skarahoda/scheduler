package io.scheduler.data.handler;

import io.scheduler.data.Meeting.DayofWeek;
import io.scheduler.data.SUClass;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FiltersSUClass {
	public static Iterable<SUClass> filterForCoReq(Iterable<SUClass> iteratable) {
		return Iterables.filter(iteratable, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return !arg0.hasCoRequisite();
			}
		});
	}

	public static Iterable<SUClass> filterForDay(Iterable<SUClass> iteratable,
			final DayofWeek day) {
		return Iterables.filter(iteratable, new Predicate<SUClass>() {
			@Override
			public boolean apply(SUClass arg0) {
				return arg0.hasMeetingAt(day);
			}
		});
	}

}
