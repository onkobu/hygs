package de.oftik.hygs.ui;

import de.oftik.keyhs.kersantti.Identifiable;

public class ExportError {
	private final Throwable throwable;
	private final Identifiable entityId;

	public ExportError(Throwable t, Identifiable entityId) {
		this.throwable = t;
		this.entityId = entityId;
	}

	public ExportError(Throwable t) {
		this(t, null);
	}

	public Identifiable getEntityId() {
		return entityId;
	}

	@Override
	public String toString() {
		return Exceptions.renderStackTrace(throwable);
	}

}
