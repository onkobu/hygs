package de.oftik.hygs.cmd.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.company.CompanyColumn;

public class DeleteCompanyCmd implements Command {
	private final long id;

	public DeleteCompanyCmd(long id) {
		super();
		this.id = id;
	}

	@Override
	public CommandTarget target() {
		return CommandTargetDefinition.company;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = delete(conn, Table.prj_company, CompanyColumn.cmp_id, CompanyColumn.cmp_deleted);
		stmt.setLong(1, id);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CompanyDeleted(id);
	}
}
