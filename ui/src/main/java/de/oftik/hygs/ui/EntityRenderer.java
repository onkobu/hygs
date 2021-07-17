package de.oftik.hygs.ui;

import java.awt.Component;
import java.util.function.Function;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class EntityRenderer<E> implements ListCellRenderer<E> {
	private final DefaultListCellRenderer delegate = new DefaultListCellRenderer();
	private final Function<E, String> toStringFunction;

	public EntityRenderer(Function<E, String> toStr) {
		this.toStringFunction = toStr;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value == null) {
			return delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
		return delegate.getListCellRendererComponent(list, toStringFunction.apply(value), index, isSelected,
				cellHasFocus);
	}
}
