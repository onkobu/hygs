package de.oftik.hygs.ui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Creates components with more context.
 * 
 * @author onkobu
 *
 */
public class ComponentFactory {
	public static JButton createButton(I18N i18n, ActionListener listener) {
		final JButton button = new JButton(i18n.label());
		button.addActionListener(listener);
		if (i18n.hasTooltip()) {
			button.setToolTipText(i18n.tooltip());
		}
		return button;
	}

	public static JLabel label(I18N i18n) {
		return new JLabel(i18n.label());
	}

	public static Component description(I18N i18n) {
		final JTextArea descArea = new JTextArea(i18n.description(), 5, 40);
		descArea.setEditable(false);
		return descArea;
	}
}
