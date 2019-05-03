package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.Table;

public interface Command {

	CommandTarget target();

	PreparedStatement prepare(Connection conn) throws SQLException;

	Notification toNotification(List<Long> generatedKeys);

	default PreparedStatement insert(Connection conn, Table t, Column<?>... cols) throws SQLException {
		final String colNames = Arrays.stream(cols).map(Column::name).collect(Collectors.joining(","));
		final String placeHolders = Arrays.stream(cols).map((c) -> "?").collect(Collectors.joining(","));
		return conn.prepareStatement("INSERT INTO " + t.name() + " (" + colNames + ") VALUES (" + placeHolders + ")",
				Statement.RETURN_GENERATED_KEYS);
	}

	default PreparedStatement update(Connection conn, Table t, Column<?> pkCol, Column<?>... cols) throws SQLException {
		final String colSets = Arrays.stream(cols).map((col) -> col.name() + "=?").collect(Collectors.joining(","));
		return conn.prepareStatement("UPDATE " + t.name() + " SET " + colSets + " WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}

	default PreparedStatement delete(Connection conn, Table t, Column<?> pkCol, Column<?> delCol) throws SQLException {
		return conn.prepareStatement(
				"UPDATE " + t.name() + " SET " + delCol.name() + "=TRUE WHERE " + pkCol.name() + "=?",
				Statement.RETURN_GENERATED_KEYS);
	}
}
