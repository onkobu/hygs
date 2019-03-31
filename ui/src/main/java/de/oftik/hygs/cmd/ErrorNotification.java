package de.oftik.hygs.cmd;

import java.util.List;

public class ErrorNotification implements Notification {
	private final CommandTarget target;
	private final EnqueueResult result;

	ErrorNotification(CommandTarget target, EnqueueResult res) {
		this.target = target;
		this.result = res;
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
