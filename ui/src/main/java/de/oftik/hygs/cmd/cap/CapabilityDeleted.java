package de.oftik.hygs.cmd.cap;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.orm.cap.Capability;

public class CapabilityDeleted extends SuccessNotification {
	public CapabilityDeleted(Capability cap) {
		super(NotificationType.TRASHED, CommandTargetDefinition.capability, cap.getId());
	}
}
