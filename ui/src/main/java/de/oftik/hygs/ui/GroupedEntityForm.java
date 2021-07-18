package de.oftik.hygs.ui;

import java.util.List;
import java.util.function.Supplier;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;

public abstract class GroupedEntityForm<G, E> extends FormPanel {

	protected GroupedEntityForm(SaveController sc, Supplier<CommandBroker> brokerSupplier) {
		super(sc, brokerSupplier);
	}

	public abstract void showEntity(G g, E e);

	protected abstract void setGroups(List<G> groups);

	public abstract void blank();

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

	public final void deleteEntity() {
		broker().execute(deleteEntityCommand());
	}

	public abstract void enableActions(boolean state);

	protected abstract boolean isNewEntity();
}
