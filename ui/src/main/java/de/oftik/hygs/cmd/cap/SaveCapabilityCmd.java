package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.cap.CapabilityColumn;
import de.oftik.hygs.query.cap.CapabilityTable;
import de.oftik.hygs.ui.cap.Category;

public class SaveCapabilityCmd extends AbstractCommand {
	private final String id;
	private final String name;
	private final String version;
	private final Category category;

	public SaveCapabilityCmd(String id, String name, String version, Category category) {
		super(CommandTargetDefinition.capability);
		this.id = id;
		this.name = name;
		this.version = version;
		this.category = category;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = update(conn, CapabilityTable.TABLE, CapabilityColumn.cap_id,
				Arrays.asList(CapabilityColumn.cap_category, CapabilityColumn.cap_name, CapabilityColumn.cap_version));
		stmt.setString(1, category.getId());
		stmt.setString(2, name);
		stmt.setString(3, version);
		stmt.setString(4, id);
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new CapabilitySaved(Capability.withId(id));
	}
}
