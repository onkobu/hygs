package de.oftik.hygs.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public abstract class EntityForm<T> extends JPanel {

	public abstract void showEntity(T t);

	protected void addTextField(I18N label, JTextField field, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(field, gbc.nextColumn().fillHorizontal().weightx(1.0).remainderX().end());
	}

	protected void addLabel(I18N label, GridBagConstraintFactory gbc) {
		add(new JLabel(label.label()), gbc.anchorEast().end());
	}

	protected void addDatePicker(I18N label, JDatePickerImpl picker, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(picker, gbc.nextColumn().fillHorizontal().end());
	}

	protected void addDescriptionArea(I18N label, JTextArea area, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(area, gbc.nextRow().fillBoth().remainderX().weightx(1.0).end());
	}
}
