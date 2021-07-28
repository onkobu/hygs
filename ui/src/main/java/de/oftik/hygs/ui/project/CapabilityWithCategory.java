package de.oftik.hygs.ui.project;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityTable;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.query.cap.CapabilityDAO;

public class CapabilityWithCategory
		implements Identifiable<Capability, CapabilityTable>, Comparable<CapabilityWithCategory> {
	private final Capability capability;
	private final Category category;

	public CapabilityWithCategory(Capability capability, Category category) {
		super();
		this.capability = capability;
		this.category = category;
	}

	public Capability getCapability() {
		return capability;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public String getId() {
		return capability.getId();
	}

	@Override
	public EntitySource<Capability, CapabilityTable> getSource() {
		return CapabilityDAO.SOURCE;
	}

	@Override
	public Capability unwrap() {
		return capability;
	}

	@Override
	public int compareTo(CapabilityWithCategory other) {
		var order = category.getName().compareTo(other.category.getName());
		if (order != 0)
			return order;
		order = capability.getName().compareTo(other.capability.getName());
		if (order != 0)
			return order;

		if (capability.getVersion() == null) {
			if (other.capability.getVersion() == null) {
				return 0;
			}
			return -1;
		}

		if (other.capability.getVersion() == null) {
			return 1;
		}

		return capability.getVersion().compareTo(other.capability.getVersion());
	}

}
