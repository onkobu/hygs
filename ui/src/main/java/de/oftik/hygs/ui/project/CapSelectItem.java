package de.oftik.hygs.ui.project;

import de.oftik.hygs.query.cap.Capability;

public class CapSelectItem {
	private final Capability capability;

	CapSelectItem(Capability capability) {
		super();
		this.capability = capability;
	}

	@Override
	public String toString() {
		return capability.getName();
	}
}
