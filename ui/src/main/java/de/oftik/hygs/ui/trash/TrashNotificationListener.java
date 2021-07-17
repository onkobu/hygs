package de.oftik.hygs.ui.trash;

import java.util.List;

import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.cmd.NotificationType;

abstract class TrashNotificationListener implements NotificationListener {
	private final CommandTarget target;

	TrashNotificationListener(CommandTarget target) {
		this.target = target;
	}

	@Override
	public CommandTarget target() {
		return target;
	}

	@Override
	public void onEnqueueError(Notification notification) {
		// simply skip errors
	}

	@Override
	public final void onSuccess(Notification notification) {
		if (notification.type() != NotificationType.TRASHED) {
			return;
		}
		handleEntityIds(notification.getIds());
	}

	abstract void handleEntityIds(List<String> ids);
}