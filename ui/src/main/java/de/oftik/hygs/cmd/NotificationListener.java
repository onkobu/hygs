package de.oftik.hygs.cmd;

public interface NotificationListener {

	CommandTarget target();

	void onEnqueueError(Notification notification);

	void onSuccess(Notification notification);

}
