package de.oftik.hygs.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import de.oftik.hygs.ui.ApplicationContext;

public abstract class DAO<T> {
	private static final Logger log = Logger.getLogger(DAO.class.getName());

	private final ApplicationContext context;
	private final Table table;

	private interface DBStatement<R> {
		R execute() throws SQLException;
	}

	public DAO(ApplicationContext context, Table table) {
		this.context = context;
		this.table = table;
	}

	public boolean isDatabaseAvailable() {
		return de.oftik.hygs.Validators.isValidPath(context.dbPath());
	}

	public List<T> findAll() throws SQLException {
		return onlyIfAvailable(() -> {
			final List<T> resultList = new ArrayList<>();
			consumeAll(resultList::add);
			return resultList;
		}, Collections.emptyList());
	}

	private <R> R onlyIfAvailable(DBStatement<R> callable, R alternative) throws SQLException {
		if (!isDatabaseAvailable()) {
			return alternative;
		}
		return callable.execute();
	}

	public Optional<T> findById(Long id) throws SQLException {
		return onlyIfAvailable(() -> {
			try (Connection conn = createConnection();) {
				final Statement statement = conn.createStatement();
				statement.setQueryTimeout(30);
				final ResultSet rs = statement
						.executeQuery("SELECT * FROM " + table.name() + " WHERE " + getPkColumn() + " = " + id + ";");
				if (rs.next()) {
					return Optional.of(map(rs));
				}
				return Optional.empty();
			}
		}, Optional.empty());
	}

	public void consumeAll(Consumer<T> consumer) throws SQLException {
		this.<Void>onlyIfAvailable(() -> {
			try (Connection conn = createConnection();) {
				final Statement statement = conn.createStatement();
				statement.setQueryTimeout(30);
				final ResultSet rs = statement.executeQuery("SELECT * FROM " + table.name() + ";");
				while (rs.next()) {
					consumer.accept(map(rs));
				}
			}
			return null;
		}, null);
	}

	protected abstract Column<?> getPkColumn();

	protected final Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:" + context.dbPath());
	}

	protected final List<T> findBy(Connection conn, Column<T> condition, Object value) throws SQLException {
		return onlyIfAvailable(() -> {
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
		}, Collections.emptyList());

	}

	protected abstract T map(ResultSet rs) throws SQLException;
}
