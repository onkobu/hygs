package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.project.AssignedCapability;
import de.oftik.hygs.query.project.ProjectCapColumn;

public class ChangeWeightCmd extends AbstractCommand {

	private final long projectId;
	private final long capabilityId;
	private final int weight;

	public ChangeWeightCmd(AssignedCapability cap, int weight) {
		super(CommandTargetDefinition.project_capability);
		this.projectId = cap.getProjectId();
		this.capabilityId = cap.getCapabilityId();
		this.weight = weight;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = conn.prepareStatement("UPDATE " + Table.prj_cap_mapping.name() + " SET "
				+ ProjectCapColumn.assigned_weight.name() + " = ? WHERE " + ProjectCapColumn.prj_id.name() + " = ? AND "
				+ ProjectCapColumn.cap_id.name() + " = ?");
		stmt.setLong(1, weight);
		stmt.setLong(2, projectId);
		stmt.setLong(3, capabilityId);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new CapabilityWeightChanged(this.projectId, this.capabilityId);
	}

}
