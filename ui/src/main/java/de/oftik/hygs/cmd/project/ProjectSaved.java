package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class ProjectSaved extends SuccessNotification {
	public ProjectSaved(long cmpId) {
		super(NotificationType.UPDATE, CommandTargetDefinition.project, cmpId);
	}
}
