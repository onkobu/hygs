package de.oftik.hygs.ui.company;

import java.awt.GridBagLayout;
import java.util.function.Supplier;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.company.CreateCompanyCmd;
import de.oftik.hygs.cmd.company.DeleteCompanyCmd;
import de.oftik.hygs.cmd.company.SaveCompanyCmd;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.SaveController;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class CompanyForm extends EntityForm<Company> {
	private final JTextField idField = new JTextField();
	private final JTextField nameField = new JTextField();
	private final JTextField streetField = new JTextField();
	private final JTextField cityField = new JTextField();
	private final JTextField zipField = new JTextField();

	public CompanyForm(SaveController sc, Supplier<CommandBroker> brokerSupplier) {
		super(sc, brokerSupplier);
		createUI();
	}

	private void createUI() {
		setLayout(new GridBagLayout());
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		idField.setColumns(10);
		idField.setEditable(false);
		nameField.setColumns(30);
		nameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkSaveStatus();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkSaveStatus();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkSaveStatus();
			}

			private void checkSaveStatus() {
				setSaveable(!isBlank(nameField));
			}
		});
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
	public Command createEntityCommand() {
		return new CreateCompanyCmd(nameField.getText(), streetField.getText(), cityField.getText(),
				zipField.getText());
	}

	@Override
	public Command saveEntityCommand() {
		return new SaveCompanyCmd(idField.getText(), nameField.getText(), streetField.getText(), cityField.getText(),
				zipField.getText());
	}

	@Override
	public Command deleteEntityCommand() {
		return new DeleteCompanyCmd(Company.withId(idField.getText()));
	}

	@Override
	public void showEntity(Company t) {
		idField.setText(String.valueOf(t.getId()));
		nameField.setText(t.getName());
		streetField.setText(t.getStreet());
		cityField.setText(t.getCity());
		zipField.setText(t.getZip());
	}

	@Override
	public void blank() {
		idField.setText(null);
		nameField.setText(null);
		streetField.setText(null);
		cityField.setText(null);
		zipField.setText(null);
	}

	@Override
	public void destroy() {
		// nothing to do here
	}

	@Override
	public boolean hasId() {
		return isBlank(idField);
	}
}
