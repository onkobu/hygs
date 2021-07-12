package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.cap.CapabilityBinding;

public class CapabilityDeleted extends SuccessNotification {
	public CapabilityDeleted(CapabilityBinding cap) {
		super(NotificationType.DELETE, CommandTargetDefinition.capability, cap.getId());
	}
}
