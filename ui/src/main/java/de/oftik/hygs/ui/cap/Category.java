package de.oftik.hygs.ui.cap;

import de.oftik.hygs.query.Identifiable;

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

	public String getName() {
		return name;
	}
}
