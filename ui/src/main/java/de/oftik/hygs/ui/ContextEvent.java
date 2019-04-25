package de.oftik.hygs.ui;

public final class ContextEvent extends Event {
	public enum ContextEventType {
		RELOAD_DATABASE;
	}

	private final ContextEventType eventType;

	private ContextEvent(ContextEventType eventType) {
		super();
		this.eventType = eventType;
	}

	public ContextEventType getEventType() {
		return eventType;
	}

	public static ContextEvent reloadDatabase() {
		return new ContextEvent(ContextEventType.RELOAD_DATABASE);
	}

}
