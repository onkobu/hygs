package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.Capability;

public class CapabilityCreated extends SuccessNotification {
	public CapabilityCreated(Capability cap) {
		super(NotificationType.INSERT, CommandTargetDefinition.capability, cap.getId());
	}
}
