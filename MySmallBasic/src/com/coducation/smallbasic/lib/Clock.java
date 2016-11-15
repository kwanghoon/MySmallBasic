package com.coducation.smallbasic.lib;

import java.util.Calendar;
import java.util.Date;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Clock {
	public static Value Date = new DoubleV(new Date().getDate());
	public static Value Day = new DoubleV(new Date().getDay());
	public static Value ElapsedMilliseconds;
	public static Value Hour = new DoubleV(new Date().getHours());
	public static Value Millisecond;
	public static Value Minute = new DoubleV(new Date().getMinutes());
	public static Value Month = new DoubleV(new Date().getMonth());
	public static Value Second = new DoubleV(new Date().getSeconds());
	public static Value Time = new StrV(new Date().getYear() + "-" + new Date().getMonth() + "-" + new Date().getDate());
	public static Value WeekDay = new DoubleV(Calendar.DAY_OF_WEEK);
	public static Value Year = new DoubleV(new Date().getYear());
}
