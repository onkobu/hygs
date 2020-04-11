package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.oftik.hygs.cmd.AbstractCommandQueue;
import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.EnqueueResult;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.Notifications;
import de.oftik.hygs.ui.ApplicationContext;

public class CapabilityQueue extends AbstractCommandQueue {
	private static final Logger log = Logger.getLogger(CapabilityQueue.class.getName());

	public CapabilityQueue(ApplicationContext context) {
		super(context, CommandTargetDefinition.capability);
	}

	@Override
	protected Notification handleCommand(Command cmd) {
		try (Connection conn = createConnection()) {
			conn.setAutoCommit(false);
			final PreparedStatement stmt = cmd.prepare(conn);
			stmt.execute();
			conn.commit();
			return cmd.toNotification(extractKeys(stmt));
		} catch (SQLException e) {
			// throwing only uses FINER
			log.log(Level.SEVERE, "handleCommand", e);
			return Notifications.enqeueError(cmd, EnqueueResult.EXECUTION_FAILED);
		}
	}

}
