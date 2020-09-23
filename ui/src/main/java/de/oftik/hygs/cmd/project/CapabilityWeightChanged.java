package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.project.Project;
import de.oftik.kehys.kersantti.ForeignKey;

public class CapabilityWeightChanged extends SuccessNotification {

	private final String capabilityId;

	public CapabilityWeightChanged(Project project, Capability capability) {
		super(NotificationType.UPDATE, CommandTargetDefinition.project_capability, project.getId());
		this.capabilityId = capability.getId();
	}

	public CapabilityWeightChanged(ForeignKey<Project> project, ForeignKey<Capability> capability) {
		super(NotificationType.UPDATE, CommandTargetDefinition.project_capability, project.getParentId());
		this.capabilityId = capability.getParentId();
	}

	public String getCapabilityId() {
		return capabilityId;
	}
}
