package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CapabilityAssigned extends SuccessNotification {

	private final long capabilityId;

	public CapabilityAssigned(long projectId, long capabilityId) {
		super(NotificationType.ASSIGNED, CommandTargetDefinition.project_capability, projectId);
		this.capabilityId = capabilityId;
	}

	public long getCapabilityId() {
		return capabilityId;
	}

}
