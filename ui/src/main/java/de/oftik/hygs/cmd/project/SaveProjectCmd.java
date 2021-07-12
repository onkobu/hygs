package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.orm.project.ProjectColumn;
import de.oftik.hygs.orm.project.ProjectTable;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.ForeignKey;

public class SaveProjectCmd extends AbstractCommand {
	private final String id;
	private final String name;
	private final LocalDate from;
	private final LocalDate to;
	private final ForeignKey<Company> companyKey;
	private final String description;
	private final int weight;

	public SaveProjectCmd(String id, String name, LocalDate from, LocalDate to, Company company, String description,
			int weight) {
		super(CommandTargetDefinition.project);
		this.id = id;
		this.name = name;
		this.from = from;
		this.to = to;
		this.companyKey = new ForeignKey<>(company);
		this.description = description;
		this.weight = weight;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = update(conn, ProjectTable.TABLE, ProjectColumn.prj_id,
				Arrays.asList(ProjectColumn.prj_name, ProjectColumn.prj_from, ProjectColumn.prj_to,
						ProjectColumn.prj_company_id, ProjectColumn.prj_description, ProjectColumn.prj_weight));
		int idx = 0;
		stmt.setString(++idx, name);
		stmt.setTimestamp(++idx, Column.toTimestamp(from));
		stmt.setTimestamp(++idx, Column.toTimestamp(to));
		stmt.setString(++idx, companyKey.getParentId());
		stmt.setString(++idx, description);
		stmt.setInt(++idx, weight);
		stmt.setString(++idx, id);// WHERE id comes last
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		try {
			return new ProjectSaved(
					AbstractCommand.loadSingle(conn, ProjectTable.TABLE, generatedKeys, new Project()).get());
		} catch (SQLException e) {
			handleException(e);
		}
		return null;
	}

}
