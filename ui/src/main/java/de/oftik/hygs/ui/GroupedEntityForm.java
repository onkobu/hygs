package de.oftik.hygs.ui;

import java.util.List;
import java.util.function.Supplier;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;

public abstract class GroupedEntityForm<G, E> extends FormPanel {
	protected GroupedEntityForm(Supplier<CommandBroker> brokerSupplier) {
		super(brokerSupplier);
	}

	public abstract void showEntity(G g, E e);

	protected abstract void setGroups(List<G> groups);

	public abstract void clearEntity();

	public abstract Command createEntityCommand();

	public abstract Command saveEntityCommand();

	public abstract Command deleteEntityCommand();

	public final void createEntity() {
		broker().execute(createEntityCommand());
	}

	public final void saveEntity() {
		broker().execute(saveEntityCommand());
	}

	public final void deleteEntity() {
		broker().execute(deleteEntityCommand());
	}

}
