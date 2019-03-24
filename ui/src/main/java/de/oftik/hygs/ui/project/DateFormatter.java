package de.oftik.hygs.ui.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFormattedTextField;

class DateFormatter extends JFormattedTextField.AbstractFormatter {
	@Override
	public String valueToString(Object value) throws ParseException {
		if (value == null) {
			return "";
		}
		final GregorianCalendar cal = (GregorianCalendar) value;
		return String.format("%td.%<tm.%<tY", cal.getTime());
	}

	@Override
	public Object stringToValue(String text) throws ParseException {
		if ("".equals(text)) {
			return null;
		}
		final Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new SimpleDateFormat("dd.mm.YYYY").parse(text));
		return cal;
	}
}