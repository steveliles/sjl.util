package com.sjl.util.dates;

import java.text.*;
import java.util.*;

import com.sjl.util.*;
import com.sjl.util.cache.*;

public class Dates {

	public static String ISO_8601_WITH_TIME = "yyyyMMdd'T'HHmmss";

	private static ThreadLocal<Calendar> CALENDAR_CACHE = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	private static ThreadLocalCache<String, DateFormat> DATEFORMAT_CACHE = new ThreadLocalCache<String, DateFormat>(
		new ValueFactory<String, DateFormat>() {
			@Override
			public DateFormat create(String aKey) {
				return new SimpleDateFormat(aKey);
			}
		});

	public static Calendar getCalendar() {
		return CALENDAR_CACHE.get();
	}

	public static DateFormat getDateFormat(String aDateFormat) {
		return DATEFORMAT_CACHE.retrieve(aDateFormat);
	}

	public static String format(Date aDate, String aDateFormat) {
		return getDateFormat(aDateFormat).format(aDate);
	}

	public static Date parse(String aDate, String aDateFormat) throws ParseException {
		return getDateFormat(aDateFormat).parse(aDate);
	}

	public static Date min(Date... aDates) {
		if (aDates.length == 0)
			return null;
		Date _min = aDates[0];
		for (Date _d : aDates)
			if (_d.before(_min))
				_min = _d;
		return _min;
	}

	public static Date max(Date... aDates) {
		if (aDates.length == 0)
			return null;
		Date _max = aDates[0];
		for (Date _d : aDates)
			if (_d.after(_max))
				_max = _d;
		return _max;
	}
}
