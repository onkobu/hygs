package de.oftik.hygs.query.cap;

public class Capability {
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
}
