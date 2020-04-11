package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CapabilityDeleted extends SuccessNotification {
	public CapabilityDeleted(long cmpId) {
		super(NotificationType.DELETE, CommandTargetDefinition.capability, cmpId);
	}
}
