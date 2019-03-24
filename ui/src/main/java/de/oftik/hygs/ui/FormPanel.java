package de.oftik.hygs.ui;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class FormPanel extends JPanel {
	public FormPanel() {
		setLayout(new GridBagLayout());
	}

	protected void addIDField(JTextField field, GridBagConstraintFactory gbc) {
		addLabel(I18N.ID, gbc);
		field.setEditable(false);
		add(field, gbc.nextColumn().fillHorizontal().weightx(0.3).end());
	}

	protected void addShortTextField(I18N label, JTextField field, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(field, gbc.nextColumn().fillHorizontal().weightx(0.3).end());
	}

	protected void addLargeTextField(I18N label, JTextField field, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(field, gbc.nextColumn().fillHorizontal().weightx(0.7).end());
	}

	protected void addRowTextField(I18N label, JTextField field, GridBagConstraintFactory gbc) {
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

	protected void add(I18N label, JComboBox<?> comboBox, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(comboBox, gbc.nextColumn().fillHorizontal().remainderX().weightx(1.0).end());
	}

	protected void add(I18N label, JTable table, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(new JScrollPane(table), gbc.nextRow().fillBoth().remainderX().remainderY().weightx(1.0).weighty(1.0).end());
	}
}
