package de.oftik.hygs.cmd;

import java.util.List;

public interface Notification {
	CommandTarget target();

	EnqueueResult result();

	List<Long> getIds();
}
