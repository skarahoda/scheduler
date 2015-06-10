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
				return "Spring";
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		result = prime * result + year;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term) obj;
		if (term != other.term)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}
