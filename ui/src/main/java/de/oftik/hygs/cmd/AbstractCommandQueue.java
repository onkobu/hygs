package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.oftik.hygs.ui.ApplicationContext;

public abstract class AbstractCommandQueue implements CommandQueue {
	private final CommandTarget target;
	private final ApplicationContext context;

	protected AbstractCommandQueue(ApplicationContext context, CommandTarget target) {
		super();
		this.target = target;
		this.context = context;
	}

	@Override
	public final CommandTarget target() {
		return target;
	}

	@Override
	public Notification enqueue(Command cmd) {
		if (cmd.target() == null) {
			return Notifications.enqeueError(cmd, EnqueueResult.TARGET_INVALID);
		}

		if (!cmd.target().equals(target)) {
			return Notifications.enqeueError(cmd, EnqueueResult.TARGET_UNSUPPORTED);
		}
		return handleCommand(cmd);
	}

	protected abstract Notification handleCommand(Command cmd);

	protected final Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:" + context.dbPath());
	}

	protected List<String> extractKeys(PreparedStatement stmt) throws SQLException {
		final ResultSet keyRes = stmt.getGeneratedKeys();
		List<String> keys = new ArrayList<>();
		while (keyRes.next()) {
			keys.add(keyRes.getString(1));
		}
		return keys;
	}

}
