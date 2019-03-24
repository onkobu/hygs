package de.oftik.hygs.ui.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.oftik.hygs.ui.ApplicationContext;

public abstract class DAO<T> {
	private final ApplicationContext context;
	private final Table table;

	public DAO(ApplicationContext context, Table table) {
		this.context = context;
		this.table = table;
	}

	public List<T> findAll() throws SQLException {
		final List<T> resultList = new ArrayList<>();
		consumeAll(resultList::add);
		return resultList;
	}

	public void consumeAll(Consumer<T> consumer) throws SQLException {
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + context.dbPath());) {
			final Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			final ResultSet rs = statement.executeQuery("SELECT * FROM " + table.name() + ";");
			while (rs.next()) {
				consumer.accept(map(rs));
			}
		}
	}

	protected abstract T map(ResultSet rs) throws SQLException;
}
