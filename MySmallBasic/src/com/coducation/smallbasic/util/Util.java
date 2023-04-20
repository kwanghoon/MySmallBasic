package com.coducation.smallbasic.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {
	public static String throwableToStacktrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		return exceptionAsString;
	}
}
