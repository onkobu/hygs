package de.oftik.hygs.ui.company;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityListPanel;

public class CompanyPanel extends EntityListPanel<Company> {

	public CompanyPanel(ApplicationContext applicationContext) {
		super(applicationContext, new CompanyDAO(applicationContext), new CompanyForm(), new CompanyCellRenderer());
	}

}
