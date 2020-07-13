package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.project.Project;

public class CapabilityAssigned extends SuccessNotification {

	private final String capabilityId;

	public CapabilityAssigned(Project project, Capability capability) {
		super(NotificationType.ASSIGNED, CommandTargetDefinition.project_capability, project.getId());
		this.capabilityId = capability.getId();
	}

	public String getCapabilityId() {
		return capabilityId;
	}

}
