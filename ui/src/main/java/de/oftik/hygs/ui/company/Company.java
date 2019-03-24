package de.oftik.hygs.ui.company;

public class Company {
	private long id;
	private String name;
	private String street;
	private String city;
	private String zip;

	public Company() {
	}

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

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
