package de.oftik.hygs.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CommandBroker {
	private final Map<String, CommandQueue> queues = new HashMap<>();
	private final Map<String, List<NotificationListener>> listenerMap = new HashMap<>();

	public void registerQueue(CommandQueue queue) {
		queues.putIfAbsent(queue.target().name(), queue);
	}

	public void registerListener(NotificationListener listener) {
		List<NotificationListener> lstnrLst;
		if ((lstnrLst = listenerMap.get(listener.target().name())) == null) {
			lstnrLst = new ArrayList<>();
			listenerMap.put(listener.target().name(), lstnrLst);
		}
		lstnrLst.add(listener);
	}

	public void execute(Command cmd) {
		final Notification res = queues.get(cmd.target().name()).enqueue(cmd);
		switch (res.result()) {
		case ACCEPTED: {
			fireSuccessful(res);
		}
			break;
		default: {
			fireError(cmd, res);
		}
		}
	}

	private void fireError(Command cmd, Notification res) {
		listenerMap.getOrDefault(cmd.target().name(), Collections.emptyList()).forEach((nl) -> nl.onEnqueueError(res));
	}

	private void fireSuccessful(Notification notification) {
		listenerMap.getOrDefault(notification.target().name(), Collections.emptyList())
				.forEach((nl) -> nl.onSuccess(notification));
	}

}
