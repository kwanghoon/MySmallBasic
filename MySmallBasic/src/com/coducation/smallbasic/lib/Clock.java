package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Clock {
	public static Value Date; // = new DoubleV(new Date().getDate());
	public static Value Day; // = new DoubleV(new Date().getDay());
	public static Value ElapsedMilliseconds;
	public static Value Hour; // = new DoubleV(new Date().getHours());
	public static Value Millisecond;
	public static Value Minute; // = new DoubleV(new Date().getMinutes());
	public static Value Month; // = new DoubleV(new Date().getMonth());
	public static Value Second; // = new DoubleV(new Date().getSeconds());
	public static Value Time; // = new StrV(new Date().getYear() + "-" + new Date().getMonth() + "-" + new Date().getDate());
	public static Value WeekDay; // = new DoubleV(Calendar.DAY_OF_WEEK);
	public static Value Year; // = new DoubleV(new Date().getYear());
	
	public static void notifyFieldAssign(String fieldName) {
		
	}
	
	public static void notifyFieldRead(String fieldName) {
		Calendar currentCal = Calendar.getInstance();
		
		if ("Date".equalsIgnoreCase(fieldName)) {
			Date = new DoubleV(currentCal.get(Calendar.DATE));
		}
		else if ("Day".equalsIgnoreCase(fieldName)) {
			Day = new DoubleV(currentCal.get(Calendar.DAY_OF_MONTH));
		}
		else if ("ElapsedMilliseconds".equalsIgnoreCase(fieldName)) {
			ElapsedMilliseconds = new DoubleV(currentCal.getTimeInMillis());
		}
		else if ("Hour".equalsIgnoreCase(fieldName)) {
			Hour = new DoubleV(currentCal.get(Calendar.HOUR_OF_DAY));
		}
		else if ("Millisecond".equalsIgnoreCase(fieldName)) {
			Millisecond = new DoubleV(currentCal.get(Calendar.MILLISECOND));
		}
		else if ("Minute".equalsIgnoreCase(fieldName)) {
			Minute = new DoubleV(currentCal.get(Calendar.MINUTE));
		}
		else if ("Month".equalsIgnoreCase(fieldName)) {
			Month = new DoubleV(currentCal.get(Calendar.MONTH)); 
		}
		else if ("Second".equalsIgnoreCase(fieldName)) {
				Second = new DoubleV(currentCal.get(Calendar.SECOND));
		}
		else if ("Time".equalsIgnoreCase(fieldName)) {
		Time = new StrV(
				(currentCal.get(Calendar.AM_PM)==Calendar.AM ? "AM " : "PM ") 
				+ currentCal.get(Calendar.HOUR) 
				+ ":" + currentCal.get(Calendar.MINUTE) 
				+ ":" + currentCal.get(Calendar.SECOND));
		}
		else if ("WeekDay".equalsIgnoreCase(fieldName)) {
			String day = "unknown";
			switch(Calendar.DAY_OF_WEEK) {
				case Calendar.MONDAY:
					day = "Monday";
					break;
				case Calendar.TUESDAY:
					day = "Tuesday";
					break;
				case Calendar.WEDNESDAY:
					day = "Wednesday";
					break;
				case Calendar.THURSDAY:
					day = "Thursday";
					break;
				case Calendar.FRIDAY:
					day = "Friday";
					break;
				case Calendar.SATURDAY:
					day = "Saturday";
					break;
				case Calendar.SUNDAY:
					day = "Sunday";
					break;	
			}
			WeekDay = new StrV(day);
		}
		else if("Year".equalsIgnoreCase(fieldName)) {
			Year = new DoubleV(currentCal.get(Calendar.YEAR));
		}
		else {
			
		}
	}
}
