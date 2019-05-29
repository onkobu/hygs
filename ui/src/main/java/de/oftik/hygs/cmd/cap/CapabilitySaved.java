package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CapabilitySaved extends SuccessNotification {
	public CapabilitySaved(long cmpId) {
		super(NotificationType.UPDATE, CommandTargetDefinition.capability, cmpId);
	}
}
