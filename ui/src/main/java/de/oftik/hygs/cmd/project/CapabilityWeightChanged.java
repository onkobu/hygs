package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CapabilityWeightChanged extends SuccessNotification {

	private final long capabilityId;

	public CapabilityWeightChanged(long projectId, long capabilityId) {
		super(NotificationType.UPDATE, CommandTargetDefinition.project_capability, projectId);
		this.capabilityId = capabilityId;
	}

}
