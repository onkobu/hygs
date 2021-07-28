package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.project.CapabilityInProject;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.query.project.ProjectCapColumn;
import de.oftik.hygs.query.project.ProjectCapTable;

public class UnassignCapabilityCmd extends AbstractCommand {
	private final Project projectId;
	private final List<CapabilityInProject> capsToDelete;

	public UnassignCapabilityCmd(Project currentProject, List<CapabilityInProject> toDelete) {
		super(CommandTargetDefinition.project_capability);
		this.projectId = currentProject;
		this.capsToDelete = toDelete;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = deleteManySecondaries(conn, ProjectCapTable.TABLE, ProjectCapColumn.prj_id,
				ProjectCapColumn.cap_id, capsToDelete.size());
		int idx = 0;
		stmt.setString(++idx, projectId.getId());
		for (var capInPrj : capsToDelete) {
			stmt.setString(++idx, capInPrj.getCapabilityId().getParentId());
		}
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new CapabilityUnassigned(projectId, generatedKeys);
	}

}
