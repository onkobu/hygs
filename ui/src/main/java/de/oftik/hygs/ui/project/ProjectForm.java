package de.oftik.hygs.ui.project;

import static de.oftik.kehys.keijukainen.gui.ListTableModel.createDescription;

import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.project.DeleteProjectCmd;
import de.oftik.hygs.query.Converters;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.project.AssignedCapability;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.MappableToStringRenderer;
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
	private final JComboBox<Company> companyBox = new JComboBox<Company>();

	private final String[] identifiers = new String[] { I18N.CAPABILITY.label(), I18N.VERSION.label(),
			I18N.WEIGHT.label() };

	private final JTable capabilityTable = new JTable();
	private CompanyCache companyCache;
	private AssignedCapabilityDAO assignedCapabilityDAO;
	private final ListTableModel<AssignedCapability> tableModel = new ListTableModel<>(
			createDescription(identifiers[0], String.class, AssignedCapability::getName),
			createDescription(identifiers[1], String.class, AssignedCapability::getVersion),
			createDescription(identifiers[2], Integer.TYPE, AssignedCapability::getWeight));

	private Project currentProject;

	public ProjectForm(Supplier<CommandBroker> brokerSupplier) {
		super(brokerSupplier);
		capabilityTable.setModel(tableModel);
		companyBox.setRenderer(new MappableToStringRenderer());
		createUI();
	}

	@Override
	public Command createEntityCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command saveEntityCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command deleteEntityCommand() {
		return new DeleteProjectCmd(Long.parseLong(idField.getText()));
	}

	void setCompanyCache(CompanyCache cc) {
		this.companyCache = cc;
		this.companyCache.addCacheListener(() -> {
			companyBox.removeAllItems();
			this.companyCache.consumeAll(companyBox::addItem);
			if (currentProject == null) {
				companyBox.setSelectedIndex(-1);
				return;
			}
			companyBox.setSelectedItem(currentProject);
		});
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

		addRowCombobox(I18N.COMPANY, companyBox, gbc.nextRow());

		add(I18N.CAPABILITY, capabilityTable, gbc.nextRow());
	}

	@Override
	public void showEntity(Project t) {
		this.currentProject = t;
		idField.setText(String.valueOf(t.getId()));
		nameField.setText(t.getName());
		fromModel.setValue(Converters.dateFromLocalDate(t.getFrom()));
		toModel.setValue(Converters.dateFromLocalDate(t.getTo()));
		descriptionArea.setText(t.getDescription());
		companyBox.setSelectedItem(companyCache.getCompanyById(t.getCompanyId()));

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

	@Override
	public void blank() {
		currentProject = null;
		idField.setText(null);
		nameField.setText(null);
		fromModel.setValue(null);
		toModel.setValue(null);
		descriptionArea.setText(null);
		companyBox.setSelectedIndex(-1);
		tableModel.clear();
	}
}
