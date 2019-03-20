package de.oftik.hygs.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions {
	public static String renderStackTrace(Throwable t) {
		final StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
