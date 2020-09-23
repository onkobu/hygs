package de.oftik.hygs.query.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.ui.cap.CategoryTable;
import de.oftik.kehys.kersantti.AbstractIdentifiable;

public class Category extends AbstractIdentifiable implements Identifiable<Category>, MappableToString {
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
	public EntitySource<Category> getSource() {
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
