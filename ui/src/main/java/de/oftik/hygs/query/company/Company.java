package de.oftik.hygs.query.company;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.query.Table;

public class Company implements Identifiable, MappableToString {
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

	@Override
	public long getId() {
		return id;
	}

	@Override
	public EntitySource getSource() {
		return Table.prj_company;
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

	@Override
	public String toShortString() {
		return String.format("%s, %s", getName(), getCity());
	}

	@Override
	public String toLongString() {
		return String.format("%s, %s, %s %s", getName(), getStreet(), getZip(), getCity());
	}
}
