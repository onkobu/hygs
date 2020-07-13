package de.oftik.hygs.cmd.company;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.company.Company;

public class CompanySaved extends SuccessNotification {
	public CompanySaved(Company cmp) {
		super(NotificationType.UPDATE, CommandTargetDefinition.company, cmp.getId());
	}
}
