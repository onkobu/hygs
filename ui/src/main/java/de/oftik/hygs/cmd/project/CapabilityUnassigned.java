package de.oftik.hygs.cmd.project;

import java.util.List;

import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.SuccessNotification;
import de.oftik.hygs.orm.project.Project;

public class CapabilityUnassigned extends SuccessNotification {

	private final List<String> capabilities;

	public CapabilityUnassigned(Project project, List<String> capabilities) {
		super(NotificationType.UNASSIGNED, CommandTargetDefinition.project_capability, project.getId());
		this.capabilities = capabilities;
	}

	public List<String> getCapabilityIds() {
		return capabilities;
	}

}
