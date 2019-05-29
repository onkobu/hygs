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
import de.oftik.hygs.ui.cap.Category;

public class CreateCapabilityCmd extends AbstractCommand {
	private final String name;
	private final String version;
	private final long category;

	public CreateCapabilityCmd(String name, String version, Category cat) {
		super(CommandTargetDefinition.capability);
		this.name = name;
		this.version = version;
		this.category = cat.getId();
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = insert(conn, Table.cap_capability, CapabilityColumn.cap_name,
				CapabilityColumn.cap_version, CapabilityColumn.cap_category);
		stmt.setString(1, name);
		stmt.setString(2, version);
		stmt.setLong(3, category);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CapabilityCreated(generatedKeys.get(0));
	}

}
