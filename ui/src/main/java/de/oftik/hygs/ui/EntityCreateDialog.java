package de.oftik.hygs.ui;

import javax.swing.JComponent;

public class EntityCreateDialog<T> extends AbstractEntityDialog<T> {
	public EntityCreateDialog(JComponent parent, EntityForm<T> main) {
		super(parent, main, main::createEntity);
	}
}
