package de.oftik.hygs.ui.project;

import java.sql.SQLException;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.cap.Category;

public class CapabilityWithCategory implements MappableToString, Identifiable<Capability> {
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
	public String toShortString() {
		return String.format("%s > %s", category.getName(), capability.toShortString());
	}

	@Override
	public String toLongString() {
		return toShortString();
	}

	@Override
	public String getId() {
		return capability.getId();
	}

	@Override
	public void recordException(SQLException e) {
		capability.recordException(e);
	}

	@Override
	public void unwindExceptions() throws SQLException {
		capability.unwindExceptions();
	}

	@Override
	public EntitySource<Capability> getSource() {
		return capability.getSource();
	}

}
