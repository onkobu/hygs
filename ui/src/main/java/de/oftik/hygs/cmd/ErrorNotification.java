package de.oftik.hygs.cmd;

import java.util.List;

import de.oftik.kehys.lippu.Immutable;

@Immutable
public class ErrorNotification implements Notification {
	private final CommandTarget target;
	private final EnqueueResult result;
	private final NotificationType type;

	ErrorNotification(NotificationType type, CommandTarget target, EnqueueResult res) {
		this.target = target;
		this.result = res;
		this.type = type;
	}

	@Override
	public NotificationType type() {
		return type;
	}

	@Override
	public CommandTarget target() {
		return target;
	}

	@Override
	public EnqueueResult result() {
		return result;
	}

	@Override
	public List<Long> getIds() {
		throw new UnsupportedOperationException("It was an error, requesting IDs does not make sense.");
	}
}
