package de.oftik.hygs.cmd;

public class Notifications {

	public static Notification enqeueError(Command cmd, EnqueueResult targetInvalid) {
		return new ErrorNotification(cmd.target(), targetInvalid);
	}

}
