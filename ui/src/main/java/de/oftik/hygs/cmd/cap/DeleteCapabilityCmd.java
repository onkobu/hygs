package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityColumn;
import de.oftik.hygs.orm.cap.CapabilityTable;

public class DeleteCapabilityCmd extends AbstractCommand {
	private final Capability capability;

	public DeleteCapabilityCmd(Capability cap) {
		super(CommandTargetDefinition.capability);
		this.capability = cap;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = trash(conn, CapabilityTable.TABLE, CapabilityColumn.cap_id,
				CapabilityColumn.cap_deleted);
		stmt.setString(1, capability.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new CapabilityDeleted(capability);
	}
}
