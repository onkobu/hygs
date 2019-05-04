package de.oftik.hygs.cmd;

public class EntityResurrected extends SuccessNotification {
	public EntityResurrected(CommandTarget dfn, long cmpId) {
		super(NotificationType.RESURRECT, dfn, cmpId);
	}
}
