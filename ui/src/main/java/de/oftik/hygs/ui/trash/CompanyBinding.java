package de.oftik.hygs.ui.trash;

import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.company.CompanyColumn;
import de.oftik.hygs.orm.company.CompanyTable;

public class CompanyBinding extends AbstractBinding<Company, CompanyTable> {
	CompanyBinding(Company c) {
		super(c, CompanyTable.TABLE, CompanyColumn.cmp_deleted);
	}

	@Override
	public String toString() {
		return "Company: " + getIdentifiable().getName();
	}
}
