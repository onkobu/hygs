package de.oftik.hygs.cmd;

public abstract class AbstractCommand implements Command {
	private final CommandTarget target;

	protected AbstractCommand(CommandTarget target) {
		this.target = target;
	}

	@Override
	public final CommandTarget target() {
		return target;
	}

}
