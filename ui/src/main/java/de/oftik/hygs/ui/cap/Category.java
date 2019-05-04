package de.oftik.hygs.ui.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.query.Table;

public class Category implements Identifiable {
	private final long id;

	private final String name;

	public Category(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public EntitySource getSource() {
		return Table.cap_category;
	}

	public String getName() {
		return name;
	}
}
