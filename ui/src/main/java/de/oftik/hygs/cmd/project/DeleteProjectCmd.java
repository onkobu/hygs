package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.orm.project.ProjectColumn;
import de.oftik.hygs.orm.project.ProjectTable;

public class DeleteProjectCmd implements Command {
	private final Project prj;

	public DeleteProjectCmd(Project prj) {
		super();
		this.prj = prj;
	}

	@Override
	public CommandTarget target() {
		return CommandTargetDefinition.project;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = trash(conn, ProjectTable.TABLE, ProjectColumn.prj_id, ProjectColumn.prj_deleted);
		stmt.setString(1, prj.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new ProjectDeleted(prj);
	}
}
