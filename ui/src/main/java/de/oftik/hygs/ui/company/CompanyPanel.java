package de.oftik.hygs.ui.company;

import java.util.function.Supplier;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.company.CompanyColumn;
import de.oftik.hygs.orm.company.CompanyTable;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityListPanel;

public class CompanyPanel extends EntityListPanel<Company, CompanyTable, CompanyForm> {

	public CompanyPanel(ApplicationContext applicationContext) {
		super(applicationContext, new CompanyDAO(applicationContext), new CompanyCellRenderer(),
				CompanyColumn.cmp_deleted, CompanyColumn.cmp_name);
		broker().registerListener(new EntityNotificationListener<Company, CompanyTable, CompanyForm>(
				CommandTargetDefinition.company, this));
	}

	@Override
	public CompanyForm createForm(Supplier<CommandBroker> brokerSupplier) {
		return new CompanyForm(this, brokerSupplier);
	}

}