package de.oftik.hygs.ui.company;

import javax.swing.JComponent;

import de.oftik.hygs.ui.EntityForm;

public class EntityCreateDialog<T> extends AbstractEntityDialog<T> {
	public EntityCreateDialog(JComponent parent, EntityForm<T> main) {
		super(parent, main, main::createEntity);
	}
}
