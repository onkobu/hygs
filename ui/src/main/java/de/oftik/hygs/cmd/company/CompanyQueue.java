package de.oftik.hygs.cmd.company;

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

public class CompanyQueue extends AbstractCommandQueue {
	private static final Logger log = Logger.getLogger(CompanyQueue.class.getName());

	public CompanyQueue(ApplicationContext context) {
		super(context, CommandTargetDefinition.company);
	}

	@Override
	protected Notification handleCommand(Command cmd) {
		try (Connection conn = createConnection()) {
			conn.setAutoCommit(false);
			final PreparedStatement stmt = cmd.prepare(conn);
			stmt.execute();
			conn.commit();
			return cmd.toNotification(conn, extractKeys(stmt));
		} catch (SQLException e) {
			// throwing only uses FINER
			log.log(Level.SEVERE, "handleCommand", e);
			return Notifications.enqeueError(cmd, EnqueueResult.EXECUTION_FAILED);
		}
	}

}
