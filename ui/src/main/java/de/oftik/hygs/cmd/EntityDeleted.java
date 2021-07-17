package de.oftik.hygs.cmd;

import de.oftik.hygs.contract.Identifiable;

public class EntityDeleted extends SuccessNotification {
	public EntityDeleted(CommandTarget dfn, Identifiable<?, ?> identifiable) {
		super(NotificationType.DELETE, dfn, identifiable.getId());
	}
}
