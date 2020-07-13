package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.Capability;

public class CapabilitySaved extends SuccessNotification {
	public CapabilitySaved(Capability cap) {
		super(NotificationType.UPDATE, CommandTargetDefinition.capability, cap.getId());
	}
}
