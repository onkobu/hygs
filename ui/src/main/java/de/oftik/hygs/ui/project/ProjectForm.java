package de.oftik.hygs.ui.project;

import java.awt.GridBagLayout;
import java.util.Properties;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.orm.Converters;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class ProjectForm extends EntityForm<Project> {
	private final JTextField idField = new JTextField();
	private final JTextField nameField = new JTextField();
	private final JTextArea descriptionArea = new JTextArea(4, 20);
	private final UtilDateModel fromModel = new UtilDateModel();
	private final UtilDateModel toModel = new UtilDateModel();
	private final JDatePickerImpl fromPicker = new JDatePickerImpl(new JDatePanelImpl(fromModel, new Properties()),
			new DateFormatter());
	private final JDatePickerImpl toPicker = new JDatePickerImpl(new JDatePanelImpl(toModel, new Properties()),
			new DateFormatter());
	private final JTextField companyNameField = new JTextField();

	private CompanyCache companyCache;

	public ProjectForm() {
		createUI();
	}

	void setCompanyCache(CompanyCache cc) {
		this.companyCache = cc;
	}

	private void createUI() {
		setLayout(new GridBagLayout());
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		idField.setColumns(10);
		idField.setEditable(false);
		nameField.setColumns(30);

		addTextField(I18N.ID, idField, gbc);
		addTextField(I18N.PROJECT, nameField, gbc.nextRow());

		addDatePicker(I18N.FROM, fromPicker, gbc.nextRow());
		addDatePicker(I18N.TO, toPicker, gbc.nextColumn());

		addDescriptionArea(I18N.DESCRIPTION, descriptionArea, gbc.nextRow());

		addTextField(I18N.COMPANY, companyNameField, gbc.nextRow());
	}

	@Override
	public void showEntity(Project t) {
		idField.setText(String.valueOf(t.getId()));
		nameField.setText(t.getName());
		fromModel.setValue(Converters.dateFromLocalDate(t.getFrom()));
		toModel.setValue(Converters.dateFromLocalDate(t.getTo()));
		descriptionArea.setText(t.getDescription());
		companyNameField.setText(companyCache.getCompanyById(t.getCompanyId()).getName());
	}
}
