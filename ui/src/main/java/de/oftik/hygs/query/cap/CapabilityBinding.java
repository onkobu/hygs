package de.oftik.hygs.query.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityTable;

public class CapabilityBinding implements Identifiable<Capability, CapabilityTable>, MappableToString {
	private Capability capability;

	public CapabilityBinding() {
	}

	public CapabilityBinding(Capability c) {
		this.capability = c;
	}

	@Override
	public String getId() {
		return capability.getId();
	}

	@Override
	public String toShortString() {
		return capability.getVersion() == null ? capability.getName()
				: String.format("%s (%s)", capability.getName(), capability.getVersion());
	}

	@Override
	public String toLongString() {
		return toShortString();
	}

	public String getName() {
		return capability.getName();
	}

	public String getVersion() {
		return capability.getVersion();
	}

	@Override
	public EntitySource<Capability, CapabilityTable> getSource() {
		return CapabilityDAO.SOURCE;
	}

	public static CapabilityBinding withId(String id2) {
		final var cap = new Capability();
		cap.setId(id2);
		final var capB = new CapabilityBinding(cap);

		return capB;
	}

	@Override
	public Capability unwrap() {
		return capability;
	}
}
