package de.oftik.hygs.query.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.query.Table;

public class Capability implements Identifiable, MappableToString {
	private final long id;
	private final String name;
	private final long categoryId;
	private final String version;

	public Capability(long id, String name, long categoryId, String version) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.version = version;
	}

	@Override
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toShortString() {
		return version == null ? getName() : String.format("%s (%s)", getName(), getVersion());
	}

	@Override
	public String toLongString() {
		return toShortString();
	}

	@Override
	public EntitySource getSource() {
		return Table.cap_capability;
	}
}
