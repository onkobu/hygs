package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.project.ProjectColumn;

public class DeleteProjectCmd implements Command {
	private final long id;

	public DeleteProjectCmd(long id) {
		super();
		this.id = id;
	}

	@Override
	public CommandTarget target() {
		return CommandTargetDefinition.project;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = delete(conn, Table.cap_project, ProjectColumn.prj_id, ProjectColumn.prj_deleted);
		stmt.setLong(1, id);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new ProjectDeleted(id);
	}
}
