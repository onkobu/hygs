package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CapabilityCreated extends SuccessNotification {
	public CapabilityCreated(long cmpId) {
		super(NotificationType.INSERT, CommandTargetDefinition.capability, cmpId);
	}
}
