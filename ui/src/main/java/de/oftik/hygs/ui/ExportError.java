package de.oftik.hygs.ui;

public class ExportError {
	private final Throwable throwable;
	private final Long entityId;

	public ExportError(Throwable t, Long entityId) {
		this.throwable = t;
		this.entityId = entityId;
	}

	public ExportError(Throwable t) {
		this(t, null);
	}

	public Long getEntityId() {
		return entityId;
	}

	@Override
	public String toString() {
		return Exceptions.renderStackTrace(throwable);
	}

}
