package de.oftik.hygs.cmd.project;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.query.project.Project;

public class ProjectSaved extends SuccessNotification {
	public ProjectSaved(Project prj) {
		super(NotificationType.UPDATE, CommandTargetDefinition.project, prj.getId());
	}
}
