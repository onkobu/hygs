package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.cap.CapabilityColumn;
import de.oftik.hygs.orm.cap.CapabilityTable;
import de.oftik.hygs.query.cap.CapabilityBinding;

public class DeleteCapabilityCmd extends AbstractCommand {
	private final CapabilityBinding capability;

	public DeleteCapabilityCmd(CapabilityBinding cap) {
		super(CommandTargetDefinition.capability);
		this.capability = cap;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = delete(conn, CapabilityTable.TABLE, CapabilityColumn.cap_id,
				CapabilityColumn.cap_deleted);
		stmt.setString(1, capability.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new CapabilityDeleted(capability);
	}
}
