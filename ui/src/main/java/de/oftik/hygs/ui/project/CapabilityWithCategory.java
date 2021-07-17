package de.oftik.hygs.ui.project;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityTable;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.query.cap.CapabilityDAO;

public class CapabilityWithCategory implements Identifiable<Capability, CapabilityTable> {
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

}
