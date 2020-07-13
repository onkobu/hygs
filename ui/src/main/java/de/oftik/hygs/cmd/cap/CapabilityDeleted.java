package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.Capability;

public class CapabilityDeleted extends SuccessNotification {
	public CapabilityDeleted(Capability cap) {
		super(NotificationType.DELETE, CommandTargetDefinition.capability, cap.getId());
	}
}
