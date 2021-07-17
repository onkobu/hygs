package de.oftik.hygs.ui.trash;

import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityColumn;
import de.oftik.hygs.orm.cap.CapabilityTable;

final class CapabilityBinding extends AbstractBinding<Capability, CapabilityTable> {
	CapabilityBinding(Capability c) {
		super(c, CapabilityTable.TABLE, CapabilityColumn.cap_deleted);
	}

	@Override
	public String toString() {
		return "Capability: " + getIdentifiable().getName()
				+ (getIdentifiable().getVersion() == null ? "" : " " + getIdentifiable().getVersion());
	}

}
