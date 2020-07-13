package de.oftik.hygs.cmd.company;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.company.Company;

public class CompanyCreated extends SuccessNotification {
	public CompanyCreated(Company cmp) {
		super(NotificationType.INSERT, CommandTargetDefinition.company, cmp.getId());
	}
}
