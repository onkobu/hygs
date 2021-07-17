package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Identifiable;
import de.oftik.kehys.kersantti.Table;

public interface Command {

	CommandTarget target();

	PreparedStatement prepare(Connection conn) throws SQLException;

	Notification toNotification(Connection conn, List<String> generatedKeys);

	default <T extends Identifiable> PreparedStatement insert(Connection conn, Table<T> t, Collection<Column<T>> cols)
			throws SQLException {
		final String colNames = cols.stream().map(Column::name).collect(Collectors.joining(","));
		final String placeHolders = cols.stream().map((c) -> "?").collect(Collectors.joining(","));
		return conn.prepareStatement("INSERT INTO " + t.name() + " (" + colNames + ") VALUES (" + placeHolders + ")",
				Statement.RETURN_GENERATED_KEYS);
	}

	default <T extends Identifiable> PreparedStatement update(Connection conn, Table<T> t, Column<T> pkCol,
			Collection<Column<T>> cols) throws SQLException {
		final String colSets = cols.stream().map((col) -> col.name() + "=?").collect(Collectors.joining(","));
		return conn.prepareStatement("UPDATE " + t.name() + " SET " + colSets + " WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}

	default <T extends Identifiable> PreparedStatement trash(Connection conn, Table<T> t, Column<T> pkCol,
			Column<T> delCol) throws SQLException {
		return conn.prepareStatement(
				"UPDATE " + t.name() + " SET " + delCol.name() + " = TRUE WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}

	default PreparedStatement delete(Connection conn, Table<?> t, Column<?> pkCol) throws SQLException {
		return conn.prepareStatement("DELETE FROM " + t.name() + " WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}

	default PreparedStatement resurrect(Connection conn, Table<?> t, Column<?> pkCol, Column<?> delCol)
			throws SQLException {
		return conn.prepareStatement(
				"UPDATE " + t.name() + " SET " + delCol.name() + "=FALSE WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}
}
