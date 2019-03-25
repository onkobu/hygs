package de.oftik.hygs.ui.project;

import static de.oftik.kehys.keijukainen.gui.ListTableModel.createDescription;

import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.oftik.hygs.query.Converters;
import de.oftik.hygs.query.project.AssignedCapability;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;
import de.oftik.kehys.keijukainen.gui.ListTableModel;

public class ProjectForm extends EntityForm<Project> {
	private static final Logger log = Logger.getLogger(ProjectForm.class.getName());

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

	private final String[] identifiers = new String[] { I18N.CAPABILITY.label(), I18N.VERSION.label(),
			I18N.WEIGHT.label() };

	private final JTable capabilityTable = new JTable();
	private CompanyCache companyCache;
	private AssignedCapabilityDAO assignedCapabilityDAO;
	private final ListTableModel<AssignedCapability> tableModel = new ListTableModel<>(
			createDescription(identifiers[0], String.class, AssignedCapability::getName),
			createDescription(identifiers[1], String.class, AssignedCapability::getVersion),
			createDescription(identifiers[2], Integer.TYPE, AssignedCapability::getWeight));

	public ProjectForm() {
		capabilityTable.setModel(tableModel);
		createUI();
	}

	void setCompanyCache(CompanyCache cc) {
		this.companyCache = cc;
	}

	void setAssignedCapabilityDAO(AssignedCapabilityDAO assCapDao) {
		this.assignedCapabilityDAO = assCapDao;
	}

	private void createUI() {
		setLayout(new GridBagLayout());
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		idField.setColumns(10);
		idField.setEditable(false);
		nameField.setColumns(30);

		addRowTextField(I18N.ID, idField, gbc);
		addRowTextField(I18N.PROJECT, nameField, gbc.nextRow());

		addDatePicker(I18N.FROM, fromPicker, gbc.nextRow());
		addDatePicker(I18N.TO, toPicker, gbc.nextColumn());

		addDescriptionArea(I18N.DESCRIPTION, descriptionArea, gbc.nextRow());

		addRowTextField(I18N.COMPANY, companyNameField, gbc.nextRow());

		add(I18N.CAPABILITY, capabilityTable, gbc.nextRow());
	}

	@Override
	public void showEntity(Project t) {
		idField.setText(String.valueOf(t.getId()));
		nameField.setText(t.getName());
		fromModel.setValue(Converters.dateFromLocalDate(t.getFrom()));
		toModel.setValue(Converters.dateFromLocalDate(t.getTo()));
		descriptionArea.setText(t.getDescription());
		companyNameField.setText(companyCache.getCompanyById(t.getCompanyId()).getName());

		try {
			final List<AssignedCapability> caps = assignedCapabilityDAO.findByProject(t);
			if (caps.isEmpty()) {
				tableModel.clear();
				return;
			}
			tableModel.set(caps);
			capabilityTable.invalidate();
		} catch (SQLException e) {
			log.throwing(getClass().getSimpleName(), "showEntity", e);
		}
	}
}
