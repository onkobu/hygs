package de.oftik.hygs.cmd;

import java.util.Arrays;
import java.util.List;

import de.oftik.kehys.lippu.Immutable;

@Immutable
public class SuccessNotification implements Notification {
	private final CommandTarget target;
	private final List<String> ids;
	private final NotificationType type;

	protected SuccessNotification(NotificationType type, CommandTarget target, String id) {
		super();
		this.target = target;
		ids = Arrays.asList(id);
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
		return EnqueueResult.ACCEPTED;
	}

	@Override
	public List<String> getIds() {
		return ids;
	}
}
