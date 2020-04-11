package de.oftik.hygs.ui.company;

import java.awt.event.ActionEvent;
import java.util.function.Supplier;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityListPanel;

public class CompanyPanel extends EntityListPanel<Company, CompanyForm> {

	public CompanyPanel(ApplicationContext applicationContext) {
		super(applicationContext, new CompanyDAO(applicationContext), new CompanyCellRenderer());
		broker().registerListener(
				new EntityNotificationListener<Company, CompanyForm>(CommandTargetDefinition.company, this));
	}

	@Override
	public CompanyForm createForm(Supplier<CommandBroker> brokerSupplier) {
		return new CompanyForm(brokerSupplier);
	}

	@Override
	public void createEntity(ActionEvent evt) {
		wrapFormAsCreateDialog().showAndWaitForDecision();
	}
}
