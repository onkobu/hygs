package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;

public class ProjectCreated extends SuccessNotification {
	public ProjectCreated(long cmpId) {
		super(NotificationType.INSERT, CommandTargetDefinition.project, cmpId);
	}
}
