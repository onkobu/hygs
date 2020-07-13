package de.oftik.hygs.cmd;

import de.oftik.keyhs.kersantti.Identifiable;

public class EntityResurrected extends SuccessNotification {
	public EntityResurrected(CommandTarget dfn, Identifiable identifiable) {
		super(NotificationType.RESURRECT, dfn, identifiable.getId());
	}
}
