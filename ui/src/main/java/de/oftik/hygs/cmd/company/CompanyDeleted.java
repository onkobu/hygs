package de.oftik.hygs.cmd.company;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CompanyDeleted extends SuccessNotification {
	public CompanyDeleted(long cmpId) {
		super(NotificationType.DELETE, CommandTargetDefinition.company, cmpId);
	}
}
