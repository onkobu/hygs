package de.oftik.hygs.cmd.company;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CompanySaved extends SuccessNotification {
	public CompanySaved(long cmpId) {
		super(NotificationType.UPDATE, CommandTargetDefinition.company, cmpId);
	}
}
