package de.oftik.hygs.ui.company;

import javax.swing.JComponent;

import de.oftik.hygs.ui.EntityForm;

public class EntitySaveDialog<T> extends AbstractEntityDialog<T> {
	public EntitySaveDialog(JComponent parent, EntityForm<T> main) {
		super(parent, main, main::saveEntity);
	}
}
