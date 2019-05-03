package de.oftik.hygs.ui;

import javax.swing.JComponent;

public class EntitySaveDialog<T> extends AbstractEntityDialog<T> {
	public EntitySaveDialog(JComponent parent, EntityForm<T> main) {
		super(parent, main, main::saveEntity);
	}
}
