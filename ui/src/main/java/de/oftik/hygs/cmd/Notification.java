package de.oftik.hygs.cmd;

import java.util.List;

public interface Notification {
	NotificationType type();

	CommandTarget target();

	EnqueueResult result();

	List<String> getIds();
}
