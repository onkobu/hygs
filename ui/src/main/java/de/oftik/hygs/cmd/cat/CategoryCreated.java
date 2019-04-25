package de.oftik.hygs.cmd.cat;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class CategoryCreated extends SuccessNotification {

	public CategoryCreated(long catId) {
		super(NotificationType.INSERT, CommandTargetDefinition.category, catId);
	}
}
