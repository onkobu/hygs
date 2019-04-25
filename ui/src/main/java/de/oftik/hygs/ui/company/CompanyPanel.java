package de.oftik.hygs.ui.company;

import java.awt.event.ActionEvent;
import java.util.function.Supplier;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.EntityListPanel;

public class CompanyPanel extends EntityListPanel<Company> {

	public CompanyPanel(ApplicationContext applicationContext) {
		super(applicationContext, new CompanyDAO(applicationContext), new CompanyCellRenderer());
		broker().registerListener(new NotificationListener() {
			@Override
			public CommandTarget target() {
				return CommandTargetDefinition.company;
			}

			@Override
			public void onEnqueueError(Notification notification) {
				// FormPanel will handle this
			}

			@Override
			public void onSuccess(Notification notification) {
				switch (notification.type()) {
				case INSERT:
					onEntityInsert(notification.getIds());
					break;
				case UPDATE:
					onEntityUpdate(notification.getIds());
					break;
				}
			}
		});
	}

	@Override
	public EntityForm<Company> createForm(Supplier<CommandBroker> brokerSupplier) {
		return new CompanyForm(brokerSupplier);
	}

	@Override
	public void createEntity(ActionEvent evt) {
		wrapFormAsCreateDialog().showAndWaitForDecision();
	}
}
