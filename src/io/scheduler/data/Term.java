package io.scheduler.data;

import java.util.Calendar;

public class Term {

	private int year;
	private TermOfYear term;

	public enum TermOfYear {
		FALL, SPRING;
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			switch (this) {
			case FALL:
				return "Fall";

			case SPRING:
				return "SPRING";
			default:
				return "";
			}
		}
	}

	/**
	 * @param year
	 * @param term
	 */
	public Term(int year, TermOfYear term) throws IllegalArgumentException {
		this.year = year;
		this.term = term;
		validate();
	}

	/**
	 * @param year
	 * @param term
	 */
	public Term(int termAndYear) throws IllegalArgumentException {
		this.year = termAndYear / 100;
		switch (termAndYear % 100) {
		case 1:
			this.term = TermOfYear.FALL;
			break;
		case 2:
			this.term = TermOfYear.SPRING;
			break;
		default:
			throw new IllegalArgumentException(termAndYear + " is not term");
		}
		validate();
	}

	private void validate() throws IllegalArgumentException {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (year > currentYear)
			throw new IllegalArgumentException(year
					+ " is higher than current year");
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the term
	 */
	public TermOfYear getTerm() {
		return term;
	}

	public int toInt() {
		return (year * 100) + term.ordinal() + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return term + " " + year;
	}
}
