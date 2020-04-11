package de.oftik.hygs.cmd.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.company.CompanyColumn;

public class SaveCompanyCmd extends AbstractCommand {
	private final long id;
	private final String name;
	private final String street;
	private final String city;
	private final String zip;

	public SaveCompanyCmd(long id, String name, String street, String city, String zip) {
		super(CommandTargetDefinition.company);
		this.id = id;
		this.name = name;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = update(conn, Table.prj_company, CompanyColumn.cmp_id, CompanyColumn.cmp_name,
				CompanyColumn.cmp_street, CompanyColumn.cmp_city, CompanyColumn.cmp_zip);
		stmt.setString(1, name);
		stmt.setString(2, street);
		stmt.setString(3, city);
		stmt.setString(4, zip);
		stmt.setLong(5, id);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CompanySaved(id);
	}
}
