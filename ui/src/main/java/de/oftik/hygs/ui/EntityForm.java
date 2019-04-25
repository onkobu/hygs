package de.oftik.hygs.ui;

import java.util.function.Supplier;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;

public abstract class EntityForm<T> extends FormPanel {
	public EntityForm(Supplier<CommandBroker> brokerSupplier) {
		super(brokerSupplier);
	}

	public abstract void showEntity(T t);

	public abstract Command createEntityCommand();

	public abstract Command saveEntityCommand();

	public void createEntity() {
		broker().execute(createEntityCommand());
	}

	public void saveEntity() {
		broker().execute(saveEntityCommand());
	}
}
