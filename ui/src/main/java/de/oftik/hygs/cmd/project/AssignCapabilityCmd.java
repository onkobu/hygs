package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.query.project.ProjectCapColumn;

public class AssignCapabilityCmd extends AbstractCommand {

	private final long projectId;
	private final long capabilityId;

	public AssignCapabilityCmd(Project currentProject, Capability selectedItem) {
		super(CommandTargetDefinition.project_capability);
		this.projectId = currentProject.getId();
		this.capabilityId = selectedItem.getId();
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = insert(conn, Table.prj_cap_mapping, ProjectCapColumn.prj_id,
				ProjectCapColumn.cap_id);
		stmt.setLong(1, projectId);
		stmt.setLong(2, capabilityId);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CapabilityAssigned(projectId, capabilityId);
	}

}
