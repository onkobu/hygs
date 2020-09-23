package de.oftik.hygs.query.company;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.kehys.kersantti.AbstractIdentifiable;

public class Company extends AbstractIdentifiable implements Identifiable<Company>, MappableToString {
	private String id;
	private String name;
	private String street;
	private String city;
	private String zip;
	private boolean deleted;

	public Company() {
	}

	public Company(String id, String name, String street, String city, String zip) {
		super();
		this.name = name;
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public boolean isDeleted() {
		return deleted;
	}

	void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public EntitySource<Company> getSource() {
		return CompanyTable.TABLE;
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

	Company setId(String id) {
		this.id = id;
		return this;
	}

	void setName(String name) {
		this.name = name;
	}

	void setStreet(String street) {
		this.street = street;
	}

	void setCity(String city) {
		this.city = city;
	}

	void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toShortString() {
		return String.format("%s, %s", getName(), getCity());
	}

	@Override
	public String toLongString() {
		return String.format("%s, %s, %s %s", getName(), getStreet(), getZip(), getCity());
	}

	public static Company withId(String id) {
		return new Company().setId(id);
	}
}
