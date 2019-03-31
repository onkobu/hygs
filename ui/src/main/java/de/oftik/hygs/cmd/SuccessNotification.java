package de.oftik.hygs.cmd;

import java.util.Arrays;
import java.util.List;

public class SuccessNotification implements Notification {
	private final CommandTarget target;
	private final List<Long> ids;

	protected SuccessNotification(CommandTarget target, long id) {
		super();
		this.target = target;
		ids = Arrays.asList(id);
	}

	@Override
	public CommandTarget target() {
		return target;
	}

	@Override
	public EnqueueResult result() {
		return EnqueueResult.ACCEPTED;
	}

	@Override
	public List<Long> getIds() {
		return ids;
	}
}
