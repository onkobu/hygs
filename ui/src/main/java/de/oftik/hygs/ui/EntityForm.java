package de.oftik.hygs.ui;

import java.util.function.Supplier;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;

public abstract class EntityForm<T> extends FormPanel {

	public EntityForm(SaveController sc, Supplier<CommandBroker> brokerSupplier) {
		super(sc, brokerSupplier);
	}

	/**
	 * Fill the view with the entities details.
	 *
	 * @param t
	 */
	public abstract void showEntity(T t);

	public abstract Command createEntityCommand();

	public abstract Command saveEntityCommand();

	public abstract Command deleteEntityCommand();

	public final void createOrSaveEntity() {
		if (isNewEntity()) {
			broker().execute(createEntityCommand());
		} else {
			broker().execute(saveEntityCommand());
		}
	}

	public void deleteEntity() {
		broker().execute(deleteEntityCommand());
	}

	/**
	 * Blank all fields due to empty selection.
	 */
	public abstract void blank();

	public abstract boolean isNewEntity();

	public abstract void enableActions(boolean state);
}
