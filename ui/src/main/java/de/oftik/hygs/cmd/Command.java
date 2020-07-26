package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Identifiable;
import de.oftik.keyhs.kersantti.Table;

public interface Command {

	CommandTarget target();

	PreparedStatement prepare(Connection conn) throws SQLException;

	Notification toNotification(Connection conn, List<String> generatedKeys);

	default <T extends Identifiable> PreparedStatement insert(Connection conn, Table<T> t, Column<T>... cols)
			throws SQLException {
		final String colNames = Arrays.stream(cols).map(Column::name).collect(Collectors.joining(","));
		final String placeHolders = Arrays.stream(cols).map((c) -> "?").collect(Collectors.joining(","));
		return conn.prepareStatement("INSERT INTO " + t.name() + " (" + colNames + ") VALUES (" + placeHolders + ")",
				Statement.RETURN_GENERATED_KEYS);
	}

	default <T extends Identifiable> PreparedStatement update(Connection conn, Table<T> t, Column<T> pkCol,
			Column<T>... cols) throws SQLException {
		final String colSets = Arrays.stream(cols).map((col) -> col.name() + "=?").collect(Collectors.joining(","));
		return conn.prepareStatement("UPDATE " + t.name() + " SET " + colSets + " WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}

	default <T extends Identifiable> PreparedStatement delete(Connection conn, Table<T> t, Column<T> pkCol,
			Column<T> delCol) throws SQLException {
		return conn.prepareStatement(
				"UPDATE " + t.name() + " SET " + delCol.name() + "=TRUE WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}

	default PreparedStatement resurrect(Connection conn, Table<?> t, Column<?> pkCol, Column<?> delCol)
			throws SQLException {
		return conn.prepareStatement(
				"UPDATE " + t.name() + " SET " + delCol.name() + "=FALSE WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}
}
