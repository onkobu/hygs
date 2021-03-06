package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.CapabilityBinding;

public class CapabilitySaved extends SuccessNotification {
	public CapabilitySaved(CapabilityBinding cap) {
		super(NotificationType.UPDATE, CommandTargetDefinition.capability, cap.getId());
	}
}
