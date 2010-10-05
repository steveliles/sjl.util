package com.sjl.util.dates;

import java.util.*;

public class DateRange {

	private static final String MILLIS = "yyyyMMddHHmmssSSS";

	private Date start;
	private Date end;
	private String millis;

	public DateRange(Date aStart, Date anEnd) {
		start = Dates.min(aStart, anEnd);
		end = Dates.max(aStart, anEnd);
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public long getDurationInMilliseconds() {
		return end.getTime() - start.getTime();
	}

	public boolean startsBefore(DateRange aDateRange) {
		if (start == null)
			return false;

		return start.before(aDateRange.start);
	}

	public boolean startsAfter(DateRange aDateRange) {
		if (start == null)
			return true;

		return start.after(aDateRange.start);
	}

	public boolean endsBefore(DateRange aDateRange) {
		if (end == null)
			return true;

		return end.before(aDateRange.end);
	}

	public boolean endsAfter(DateRange aDateRange) {
		if (end == null)
			return false;

		return end.after(aDateRange.end);
	}

	public boolean encompasses(DateRange aDateRange) {
		return startsBefore(aDateRange) && endsAfter(aDateRange);
	}

	public boolean encompasses(Date aDate) {
		if (start != null)
			if (start.after(aDate))
				return false;
		if (end != null)
			if (end.before(aDate))
				return false;

		return true;
	}

	public boolean equals(Object anObject) {
		if (!(anObject instanceof DateRange))
			return false;

		DateRange _other = (DateRange) anObject;
		return (toMillisecondsString().equals(_other.toString()));
	}

	private String toMillisecondsString() {
		if (millis == null)
			millis = toString(MILLIS);
		return millis;
	}

	public int hashCode() {
		return toMillisecondsString().hashCode() ^ getClass().hashCode();
	}

	public String toString() {
		return toString(Dates.ISO_8601_WITH_TIME);
	}

	public String toString(String aDateFormat) {
		StringBuilder _sb = new StringBuilder((aDateFormat.length() * 2) + 6);
		_sb.append("[").append(Dates.format(start, aDateFormat));
		_sb.append(" TO ").append(Dates.format(end, aDateFormat)).append("]");
		return _sb.toString();
	}

}
