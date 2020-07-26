package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.query.project.ProjectCapColumn;
import de.oftik.hygs.query.project.ProjectCapTable;

public class AssignCapabilityCmd extends AbstractCommand {

	private final Project projectId;
	private final Capability capabilityId;

	public AssignCapabilityCmd(Project currentProject, Capability selectedItem) {
		super(CommandTargetDefinition.project_capability);
		this.projectId = currentProject;
		this.capabilityId = selectedItem;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = insert(conn, ProjectCapTable.TABLE,
				Arrays.asList(ProjectCapColumn.prj_id, ProjectCapColumn.cap_id));
		stmt.setString(1, projectId.getId());
		stmt.setString(2, capabilityId.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new CapabilityAssigned(projectId, capabilityId);
	}

}
