package de.oftik.hygs.cmd.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.company.CompanyColumn;
import de.oftik.hygs.orm.company.CompanyTable;

public class DeleteCompanyCmd extends AbstractCommand {
	private final String id;

	public DeleteCompanyCmd(Company comp) {
		super(CommandTargetDefinition.company);
		this.id = comp.getId();
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = delete(conn, CompanyTable.TABLE, CompanyColumn.cmp_id,
				CompanyColumn.cmp_deleted);
		stmt.setString(1, id);
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		final var c = new Company();
		c.setId(id);
		return new CompanyDeleted(c);
	}
}
