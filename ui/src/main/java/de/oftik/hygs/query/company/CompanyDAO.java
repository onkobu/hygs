package de.oftik.hygs.query.company;

import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;

public class CompanyDAO extends AbstractDao<Company> {

	public CompanyDAO(ApplicationContext context) {
		super(context, CompanyTable.TABLE);
	}

	@Override
	protected Company instantiate() {
		return new Company();
	}

}
