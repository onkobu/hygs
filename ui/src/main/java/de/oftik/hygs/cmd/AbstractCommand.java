package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import de.oftik.keyhs.kersantti.Identifiable;
import de.oftik.keyhs.kersantti.Table;

public abstract class AbstractCommand implements Command {
	private static final Logger LOG = Logger.getLogger(AbstractCommand.class.getName());

	private final CommandTarget target;

	protected AbstractCommand(CommandTarget target) {
		this.target = target;
	}

	@Override
	public final CommandTarget target() {
		return target;
	}

	public static <T extends Identifiable> Optional<T> loadSingle(Connection conn, Table<T> table, List<String> id, T t)
			throws SQLException {
		final PreparedStatement statement = conn
				.prepareStatement("SELECT * FROM " + table.name() + " WHERE " + table.getPkColumn().name() + " = ?;");
		statement.setString(1, id.get(0));
		final ResultSet res = statement.executeQuery();
		if (res.next()) {
			return Optional.of(table.mapAll(t, res));
		}
		return Optional.empty();
	}

	protected void handleException(SQLException ex) {
		LOG.throwing(getClass().getSimpleName(), "handleException", ex);
	}
}
