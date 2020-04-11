package de.oftik.hygs.ui;

import javax.swing.JComponent;

public class EntityCreateDialog<T, F extends EntityForm<T>> extends AbstractEntityDialog<T, F> {
	public EntityCreateDialog(JComponent parent, F main) {
		super(parent, main, main::createEntity);
	}
}
