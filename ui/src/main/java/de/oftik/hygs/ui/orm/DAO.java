package de.oftik.hygs.ui.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import de.oftik.hygs.ui.ApplicationContext;

public abstract class DAO<T> {
	private static final Logger log = Logger.getLogger(DAO.class.getName());

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
		try (Connection conn = createConnection();) {
			final Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			final ResultSet rs = statement.executeQuery("SELECT * FROM " + table.name() + ";");
			while (rs.next()) {
				consumer.accept(map(rs));
			}
		}
	}

	protected final Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:" + context.dbPath());
	}

	protected final List<T> findBy(Connection conn, Column condition, Object value) throws SQLException {
		final PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + table.name() + " WHERE " + condition.name() + " = ?");
		stmt.setObject(1, value);
		final ResultSet rs = stmt.executeQuery();
		final List<T> entities = new ArrayList<>();
		while (rs.next()) {
			entities.add(map(rs));
		}
		log.info(String.format("Received %d entities for %s = %s", entities.size(), condition, value));
		return entities;
	}

	protected abstract T map(ResultSet rs) throws SQLException;
}
