package de.oftik.hygs.cmd.cap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.company.CompanyColumn;
import de.oftik.hygs.ui.cap.Category;

public class SaveCapabilityCmd extends AbstractCommand {
	private final long id;
	private final String name;
	private final String version;
	private final long category;

	public SaveCapabilityCmd(long id, String name, String version, Category category) {
		super(CommandTargetDefinition.capability);
		this.id = id;
		this.name = name;
		this.version = version;
		this.category = category.getId();
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = update(conn, Table.cap_capability, CompanyColumn.cmp_id, CompanyColumn.cmp_name,
				CompanyColumn.cmp_street, CompanyColumn.cmp_city, CompanyColumn.cmp_zip);
		stmt.setString(1, name);
		stmt.setString(2, version);
		stmt.setLong(3, category);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CapabilitySaved(id);
	}
}
