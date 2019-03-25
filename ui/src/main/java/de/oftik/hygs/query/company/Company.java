package de.oftik.hygs.query.company;

public class Company {
	private final long id;
	private final String name;
	private final String street;
	private final String city;
	private final String zip;

	public Company(long id, String name, String street, String city, String zip) {
		super();
		this.name = name;
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}
}
