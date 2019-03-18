package de.oftik.hygs.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {
	private final ApplicationContext context;
	private final Table table;

	public DAO(ApplicationContext context, Table table) {
		this.context = context;
		this.table = table;
	}

	public List<T> findAll() throws SQLException {
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + context.dbPath());) {
			final Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			final ResultSet rs = statement.executeQuery("SELECT * FROM " + table.name() + ";");
			final List<T> resultList = new ArrayList<>();
			while (rs.next()) {
				resultList.add(map(rs));
			}
			return resultList;
		}
	}

	protected abstract T map(ResultSet rs) throws SQLException;
}
