package de.oftik.hygs.cmd.company;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.orm.company.Company;

public class CompanyDeleted extends SuccessNotification {
	public CompanyDeleted(Company cmp) {
		super(NotificationType.TRASHED, CommandTargetDefinition.company, cmp.getId());
	}
}
