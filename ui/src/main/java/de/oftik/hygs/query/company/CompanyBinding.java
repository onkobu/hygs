package de.oftik.hygs.query.company;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.company.CompanyTable;

public class CompanyBinding implements Identifiable<Company, CompanyTable>, MappableToString {
	private Company company;

	public CompanyBinding(Company c) {
		this.company = c;
	}

	@Override
	public String toShortString() {
		return company.getName();
	}

	@Override
	public String toLongString() {
		// TODO: something with an address
		return toShortString();
	}

	@Override
	public EntitySource<Company, CompanyTable> getSource() {
		return CompanyDAO.SOURCE;
	}

	@Override
	public String getId() {
		return company.getId();
	}

	@Override
	public Company unwrap() {
		return company;
	}

}
