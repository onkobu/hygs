package de.oftik.hygs.cmd.company;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CompanyCreated extends SuccessNotification {
	public CompanyCreated(long cmpId) {
		super(NotificationType.INSERT, CommandTargetDefinition.company, cmpId);
	}
}
