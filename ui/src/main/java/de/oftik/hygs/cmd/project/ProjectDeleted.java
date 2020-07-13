package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.project.Project;

public class ProjectDeleted extends SuccessNotification {
	public ProjectDeleted(Project prj) {
		super(NotificationType.DELETE, CommandTargetDefinition.project, prj.getId());
	}
}
