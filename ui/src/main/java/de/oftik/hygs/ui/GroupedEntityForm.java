package de.oftik.hygs.ui;

import java.util.List;

public abstract class GroupedEntityForm<G, E> extends FormPanel {

	public abstract void showEntity(G g, E e);

	protected abstract void setGroups(List<G> groups);

}
