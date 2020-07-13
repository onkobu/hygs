package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.project.Project;

public class ProjectCreated extends SuccessNotification {
	public ProjectCreated(Project prj) {
		super(NotificationType.INSERT, CommandTargetDefinition.project, prj.getId());
	}
}
