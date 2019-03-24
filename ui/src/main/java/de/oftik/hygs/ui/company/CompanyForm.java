package de.oftik.hygs.ui.company;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class CompanyForm extends EntityForm<Company> {
	private final JTextField idField = new JTextField();
	private final JTextField nameField = new JTextField();
	private final JTextField streetField = new JTextField();
	private final JTextField cityField = new JTextField();
	private final JTextField zipField = new JTextField();

	public CompanyForm() {
		createUI();
	}

	private void createUI() {
		setLayout(new GridBagLayout());
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		idField.setColumns(10);
		idField.setEditable(false);
		nameField.setColumns(30);
		streetField.setColumns(nameField.getColumns());
		cityField.setColumns(idField.getColumns());
		zipField.setColumns(idField.getColumns());

		addRowTextField(I18N.ID, idField, gbc);
		addRowTextField(I18N.COMPANY, nameField, gbc.nextRow());
		addRowTextField(I18N.STREET, streetField, gbc.nextRow());

		addLabel(I18N.CITY, gbc.nextRow());
		add(cityField, gbc.nextColumn().end());
		addLabel(I18N.ZIP, gbc.nextColumn());
		add(zipField, gbc.nextColumn().remainderX().end());
	}

	@Override
	public void showEntity(Company t) {
		idField.setText(String.valueOf(t.getId()));
		nameField.setText(t.getName());
		streetField.setText(t.getStreet());
		cityField.setText(t.getCity());
		zipField.setText(t.getZip());
	}
}
