package de.oftik.hygs.cmd.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.cmd.AbstractCommand;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.project.CapabilityInProject;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.query.project.ProjectCapColumn;
import de.oftik.hygs.query.project.ProjectCapTable;
import de.oftik.kehys.kersantti.ForeignKey;

public class ChangeWeightCmd extends AbstractCommand {

	private final ForeignKey<Project> projectId;
	private final ForeignKey<Capability> capabilityId;
	private final int weight;

	public ChangeWeightCmd(CapabilityInProject cap, int weight) {
		super(CommandTargetDefinition.project_capability);
		this.projectId = cap.getProjectId();
		this.capabilityId = cap.getCapabilityId();
		this.weight = weight;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = conn.prepareStatement("UPDATE " + ProjectCapTable.TABLE.name() + " SET "
				+ ProjectCapColumn.assigned_weight.name() + " = ? WHERE " + ProjectCapColumn.prj_id.name() + " = ? AND "
				+ ProjectCapColumn.cap_id.name() + " = ?");
		stmt.setLong(1, weight);
		stmt.setString(2, projectId.getParentId());
		stmt.setString(3, capabilityId.getParentId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new CapabilityWeightChanged(this.projectId, this.capabilityId);
	}

}
