package de.oftik.hygs.cmd;

public interface CommandQueue {
	CommandTarget target();

	Notification enqueue(Command cmd);
}
