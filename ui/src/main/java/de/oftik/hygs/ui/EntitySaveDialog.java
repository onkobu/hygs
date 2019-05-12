package de.oftik.hygs.ui;

import javax.swing.JComponent;

public class EntitySaveDialog<T, F extends EntityForm<T>> extends AbstractEntityDialog<T, F> {
	public EntitySaveDialog(JComponent parent, F main) {
		super(parent, main, main::saveEntity);
	}
}
