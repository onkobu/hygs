package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.cap.CapabilityColumn;

public class DeleteCapabilityCmd extends AbstractCommand {
	private final long id;

	public DeleteCapabilityCmd(long id) {
		super(CommandTargetDefinition.capability);
		this.id = id;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = delete(conn, Table.cap_capability, CapabilityColumn.cap_id,
				CapabilityColumn.cap_deleted);
		stmt.setLong(1, id);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CapabilityDeleted(id);
	}
}
