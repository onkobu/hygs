package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class ProjectDeleted extends SuccessNotification {
	public ProjectDeleted(long cmpId) {
		super(NotificationType.DELETE, CommandTargetDefinition.project, cmpId);
	}
}
