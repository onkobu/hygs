package de.oftik.hygs.ui;

import javax.swing.JComponent;

public class GroupedEntityCreateDialog<G, E, F extends GroupedEntityForm<G, E>> extends AbstractEntityDialog<E, F> {
	public GroupedEntityCreateDialog(JComponent parent, F main) {
		super(parent, main, main::createEntity);
	}
}
