package de.oftik.hygs.query.company;

import de.oftik.hygs.contract.EntitySourceFixture;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.company.CompanyColumn;
import de.oftik.hygs.orm.company.CompanyTable;
import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;

public class CompanyDAO extends AbstractDao<Company, CompanyTable> {

	static final EntitySourceFixture<Company, CompanyTable> SOURCE = new EntitySourceFixture<Company, CompanyTable>(
			CompanyTable.TABLE, CompanyColumn.cmp_deleted);

	public CompanyDAO(ApplicationContext context) {
		super(context, SOURCE);
	}

	@Override
	protected Company instantiate() {
		return new Company();
	}

}
