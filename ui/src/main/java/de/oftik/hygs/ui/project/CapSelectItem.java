package de.oftik.hygs.ui.project;

import de.oftik.hygs.query.cap.CapabilityBinding;

public class CapSelectItem {
	private final CapabilityBinding capability;

	CapSelectItem(CapabilityBinding capability) {
		super();
		this.capability = capability;
	}

	@Override
	public String toString() {
		return capability.toShortString();
	}
}
