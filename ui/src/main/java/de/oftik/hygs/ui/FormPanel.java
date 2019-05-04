package de.oftik.hygs.ui;

import java.awt.GridBagLayout;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class FormPanel extends JPanel {
	private final Supplier<CommandBroker> brokerSupplier;

	public FormPanel(Supplier<CommandBroker> brokerSupplier) {
		this.brokerSupplier = brokerSupplier;
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

	protected void addButton(I18N label, ButtonCallback callback, GridBagConstraintFactory gbc) {
		final JButton button = new JButton(label.label());
		button.addActionListener((evt) -> callback.buttonPressed());
		add(button, gbc.end());
	}

	protected void addLargeTextField(I18N label, JTextField field, GridBagConstraintFactory gbc) {
		addLabel(label, gbc);
		add(field, gbc.nextColumn().fillHorizontal().weightx(0.7).end());
	}

	protected void addRowTextField(I18N label, JTextField field, GridBagConstraintFactory gbc) {
		addRowComponent(label, field, gbc);
	}

	protected void addRowCombobox(I18N label, JComboBox<?> box, GridBagConstraintFactory gbc) {
		addRowComponent(label, box, gbc);
	}

	private void addRowComponent(I18N label, JComponent field, GridBagConstraintFactory gbc) {
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

	protected CommandBroker broker() {
		return brokerSupplier.get();
	}
}