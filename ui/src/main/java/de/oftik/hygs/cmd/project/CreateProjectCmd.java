package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.query.project.ProjectColumn;
import de.oftik.hygs.query.project.ProjectTable;
import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ForeignKey;

public class CreateProjectCmd extends AbstractCommand {
	private String name;
	private LocalDate from;
	private LocalDate to;
	private ForeignKey<Company> companyKey;
	private String description;
	private int weight;

	public CreateProjectCmd(String name, LocalDate from, LocalDate to, Company company, String description,
			int weight) {
		super(CommandTargetDefinition.project);
		this.name = name;
		this.from = from;
		this.to = to;
		this.companyKey = new ForeignKey<>(company);
		this.description = description;
		this.weight = weight;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = insert(conn, ProjectTable.TABLE, ProjectColumn.prj_name, ProjectColumn.prj_from,
				ProjectColumn.prj_to, ProjectColumn.prj_company, ProjectColumn.prj_description,
				ProjectColumn.prj_weight);
		int idx = 0;
		stmt.setString(++idx, name);
		stmt.setTimestamp(++idx, Column.toTimestamp(from));
		stmt.setTimestamp(++idx, Column.toTimestamp(to));
		stmt.setString(++idx, companyKey.getParentId());
		stmt.setString(++idx, description);
		stmt.setInt(++idx, weight);
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		try {
			return new ProjectCreated(
					AbstractCommand.loadSingle(conn, ProjectTable.TABLE, generatedKeys, new Project()).get());
		} catch (SQLException e) {
			handleException(e);
		}
		return null;
	}
}
