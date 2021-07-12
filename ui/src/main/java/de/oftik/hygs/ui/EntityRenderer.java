package de.oftik.hygs.ui;

import java.awt.Component;
import java.util.function.Function;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.oftik.kehys.kersantti.Identifiable;

public class EntityRenderer<I extends Identifiable> implements ListCellRenderer<I> {
	private final DefaultListCellRenderer delegate = new DefaultListCellRenderer();
	private final Function<I, String> toStringFunction;

	public EntityRenderer(Function<I, String> toStr) {
		this.toStringFunction = toStr;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends I> list, I value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value == null) {
			return delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
		return delegate.getListCellRendererComponent(list, toStringFunction.apply(value), index, isSelected,
				cellHasFocus);
	}
}
