package de.oftik.hygs.cmd.cat;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.ui.cap.Category;

public class CategoryCreated extends SuccessNotification {

	public CategoryCreated(Category cat) {
		super(NotificationType.INSERT, CommandTargetDefinition.category, cat.getId());
	}
}
