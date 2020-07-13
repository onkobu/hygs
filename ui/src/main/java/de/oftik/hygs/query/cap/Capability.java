package de.oftik.hygs.query.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;

public class Capability implements Identifiable, MappableToString {
	private String id;
	private String name;
	private long categoryId;
	private String version;
	private boolean deleted;

	public Capability() {
	}

	public Capability(String id, String name, long categoryId, String version) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.version = version;
	}

	@Override
	public String getId() {
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

	public boolean isDeleted() {
		return deleted;
	}

	void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toShortString() {
		return version == null ? getName() : String.format("%s (%s)", getName(), getVersion());
	}

	Capability setId(String id) {
		this.id = id;
		return this;
	}

	void setName(String name) {
		this.name = name;
	}

	void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toLongString() {
		return toShortString();
	}

	@Override
	public EntitySource<Capability> getSource() {
		return CapabilityTable.TABLE;
	}

	public static Capability withId(String id2) {
		return new Capability().setId(id2);
	}
}
