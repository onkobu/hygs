package de.oftik.hygs.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;

public class MappableToStringRenderer implements ListCellRenderer<Identifiable<?, ?>> {
	private final DefaultListCellRenderer delegate = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList<? extends Identifiable<?, ?>> list, Identifiable<?, ?> value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof MappableToString) {
			return delegate.getListCellRendererComponent(list, ((MappableToString) value).toShortString(), index,
					isSelected, cellHasFocus);
		}
		return delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}