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

public class CreateCompanyCmd extends AbstractCommand {
	private final String name;
	private final String street;
	private final String city;
	private final String zip;

	public CreateCompanyCmd(String name, String street, String city, String zip) {
		super(CommandTargetDefinition.company);
		this.name = name;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = insert(conn, Table.prj_company, CompanyColumn.cmp_name, CompanyColumn.cmp_street,
				CompanyColumn.cmp_city, CompanyColumn.cmp_zip);
		stmt.setString(1, name);
		stmt.setString(2, street);
		stmt.setString(3, city);
		stmt.setString(4, zip);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CompanyCreated(generatedKeys.get(0));
	}

}
