package de.oftik.hygs.ui.company;

import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityListPanel;

public class CompanyPanel extends EntityListPanel<Company> {

	public CompanyPanel(ApplicationContext applicationContext) {
		super(applicationContext, new CompanyDAO(applicationContext), new CompanyForm(applicationContext::getBroker),
				new CompanyCellRenderer());
	}

}
