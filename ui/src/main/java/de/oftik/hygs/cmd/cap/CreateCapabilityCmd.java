package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityColumn;
import de.oftik.hygs.orm.cap.CapabilityTable;
import de.oftik.hygs.orm.cap.Category;

public class CreateCapabilityCmd extends AbstractCommand {
	private final String name;
	private final String version;
	private final Category category;

	public CreateCapabilityCmd(String name, String version, Category cat) {
		super(CommandTargetDefinition.capability);
		this.name = name;
		this.version = version;
		this.category = cat;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = insert(conn, CapabilityTable.TABLE, Arrays.asList(CapabilityColumn.cap_name,
				CapabilityColumn.cap_version, CapabilityColumn.cap_category_id));
		stmt.setString(1, name);
		stmt.setString(2, version);
		stmt.setString(3, category.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		try {
			final var cap = loadSingle(conn, CapabilityTable.TABLE, generatedKeys, new Capability());
			return new CapabilityCreated(cap.get());
		} catch (SQLException ex) {
			handleException(ex);
		}
		return null;
	}

}
