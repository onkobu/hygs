package de.oftik.hygs.ui.orm;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public interface Column<T> {
	String name();

	void toPojo(ResultSet rs, T t) throws SQLException;

	default LocalDate asLocalDate(ResultSet rs) throws SQLException {
		final Date value = rs.getDate(name());
		if (value == null) {
			return null;
		}
		return value.toLocalDate();
	}

	default String asString(ResultSet rs) throws SQLException {
		return rs.getString(name());
	}

	default long asLong(ResultSet rs) throws SQLException {
		return rs.getLong(name());
	}

	default int asInt(ResultSet rs) throws SQLException {
		return rs.getInt(name());
	}
}
