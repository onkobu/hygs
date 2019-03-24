package de.oftik.hygs.ui.orm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Converters {
	public static Date dateFromLocalDate(LocalDate ld) {
		if (ld == null) {
			return null;
		}
		return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
