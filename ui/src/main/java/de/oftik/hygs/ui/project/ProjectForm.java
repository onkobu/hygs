package de.oftik.hygs.ui.project;

import static de.oftik.kehys.keijukainen.gui.ListTableModel.createDescription;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.project.AssignCapabilityCmd;
import de.oftik.hygs.cmd.project.ChangeWeightCmd;
import de.oftik.hygs.cmd.project.DeleteProjectCmd;
import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.contract.CacheType;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.project.AssignedCapability;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.ui.ComponentFactory;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.MappableToStringRenderer;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;
import de.oftik.kehys.keijukainen.gui.ListTableModel;
import de.oftik.keyhs.kersantti.query.Converters;

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
	private final JComboBox<Company> companyBox = new JComboBox<>();
	private final JComboBox<Capability> capabilityBox = new JComboBox<>();
	private final String[] identifiers = new String[] { I18N.CAPABILITY.label(), I18N.VERSION.label(),
			I18N.WEIGHT.label() };

	private final JTable capabilityTable = new JTable();
	private CompanyCache companyCache;
	private CapabilityCache capabilityCache;

	private AssignedCapabilityDAO assignedCapabilityDAO;
	private final ListTableModel<AssignedCapability> tableModel = new ListTableModel<AssignedCapability>(
			createDescription(identifiers[0], String.class, AssignedCapability::getName),
			createDescription(identifiers[1], String.class, AssignedCapability::getVersion),
			createDescription(identifiers[2], Number.class, AssignedCapability::getWeight, true)) {

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex != 2) {
				throw new UnsupportedOperationException();
			}
			changeCapabilityWeight(getRow(rowIndex), Integer.parseInt(String.valueOf(aValue)));
		}
	};

	private Project currentProject;

	private final boolean createMode;
	private final JButton addCapButton = ComponentFactory.createButton(I18N.ADD, this::addCapability);

	class CacheListenerImpl implements CacheListener {
		@Override
		public void refresh(CacheType ct) {
			if (ct == ProjectPanel.Cache.COMPANY) {
				refreshCompanies();
			} else if (ct == ProjectPanel.Cache.CAPABILITY) {
				refreshCapabilities();
			}
		}

		private void refreshCapabilities() {
			capabilityBox.removeAllItems();
			capabilityCache.consumeAllCapabilities(capabilityBox::addItem);
		}

		private void refreshCompanies() {
			companyBox.removeAllItems();
			companyCache.consumeAllCompanies(companyBox::addItem);
			if (currentProject == null) {
				companyBox.setSelectedIndex(-1);
				return;
			}
			companyBox.setSelectedItem(currentProject);
		};

	}

	private final CacheListener cacheListener = new CacheListenerImpl();

	public ProjectForm(Supplier<CommandBroker> brokerSupplier) {
		this(brokerSupplier, false);
	}

	public ProjectForm(Supplier<CommandBroker> brokerSupplier, boolean createMode) {
		super(brokerSupplier);
		this.createMode = createMode;
		capabilityTable.setModel(tableModel);
		capabilityTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
		companyBox.setRenderer(new MappableToStringRenderer());
		capabilityBox.setRenderer(new MappableToStringRenderer());
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
		return new DeleteProjectCmd(Project.withId(idField.getText()));
	}

	void setCompanyCache(CompanyCache cc) {
		this.companyCache = cc;
		this.companyCache.consumeAllCompanies(companyBox::addItem);
		this.companyCache.addCacheListener(cacheListener);
	}

	void setCapabilityCache(CapabilityCache cc) {
		this.capabilityCache = cc;
		this.capabilityCache.consumeAllCapabilities(capabilityBox::addItem);
		this.capabilityCache.addCacheListener(cacheListener);
	}

	void setAssignedCapabilityDAO(AssignedCapabilityDAO assCapDao) {
		this.assignedCapabilityDAO = assCapDao;
	}

	void changeCapabilityWeight(AssignedCapability cap, int weight) {
		broker().execute(new ChangeWeightCmd(cap, weight));
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

		final JPanel editCapPanel = new JPanel();
		editCapPanel.setLayout(new FlowLayout());
		editCapPanel.add(capabilityBox);
		editCapPanel.add(addCapButton);
		add(editCapPanel, gbc.nextRow().nextColumn().remainderX().end());
	}

	@SuppressWarnings("PMD.UnusedFormalParameter")
 	public void addCapability(ActionEvent evt) {
		if (!createMode) {
			broker().execute(new AssignCapabilityCmd(currentProject, (Capability) capabilityBox.getSelectedItem()));
		}
	}

	@Override
	public void showEntity(Project t) {
		this.currentProject = t;
		addCapButton.setEnabled(true);
		idField.setText(String.valueOf(t.getId()));
		nameField.setText(t.getName());
		fromModel.setValue(Converters.dateFromLocalDate(t.getFrom()));
		toModel.setValue(Converters.dateFromLocalDate(t.getTo()));
		descriptionArea.setText(t.getDescription());
		companyBox.setSelectedItem(companyCache.getCompany(t.getCompany()));
		refreshCapabilities();
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
		addCapButton.setEnabled(false);
	}

	@Override
	public void destroy() {
		if (companyCache == null) {
			return;
		}
		companyCache.removeCacheListener(cacheListener);
	}

	public void refreshCapabilities() {
		try {
			final List<AssignedCapability> caps = assignedCapabilityDAO.findByProject(currentProject);
			if (caps.isEmpty()) {
				tableModel.clear();
				return;
			}
			tableModel.set(caps);
//			tableModel.fire
			capabilityTable.invalidate();
		} catch (SQLException e) {
			log.throwing(getClass().getSimpleName(), "showEntity", e);
		}

	}
}
