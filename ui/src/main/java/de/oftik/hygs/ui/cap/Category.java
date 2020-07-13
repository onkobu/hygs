package de.oftik.hygs.ui.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;

public class Category implements Identifiable, MappableToString {
	private String id;

	private String name;

	private boolean deleted;

	public Category() {
	}

	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public EntitySource getSource() {
		return CategoryTable.TABLE;
	}

	public String getName() {
		return name;
	}

	void setId(String id) {
		this.id = id;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public String toShortString() {
		return getName();
	}

	@Override
	public String toLongString() {
		return getName();
	}
}
