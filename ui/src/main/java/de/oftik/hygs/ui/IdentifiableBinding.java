package de.oftik.hygs.ui;

import de.oftik.kehys.kersantti.Identifiable;

public abstract class IdentifiableBinding<I extends Identifiable> {
	private final I identifiable;

	public IdentifiableBinding(I identifiable) {
		this.identifiable = identifiable;
	}

	public I getIdentifiable() {
		return identifiable;
	}

	public String getId() {
		return getIdentifiable().getId();
	}

	public abstract boolean matchesTerm(String term);
}
