package de.oftik.hygs.ui;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ComponentFactory {
	public static JButton createButton(I18N i18n, ActionListener listener) {
		final JButton button = new JButton(i18n.label());
		button.addActionListener(listener);
		if (i18n.hasTooltip()) {
			button.setToolTipText(i18n.tooltip());
		}
		return button;
	}
}
