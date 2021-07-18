package de.oftik.hygs.ui.project;

import static de.oftik.kehys.keijukainen.gui.ListTableModel.createDescription;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.project.AssignCapabilityCmd;
import de.oftik.hygs.cmd.project.ChangeWeightCmd;
import de.oftik.hygs.cmd.project.CreateProjectCmd;
import de.oftik.hygs.cmd.project.DeleteProjectCmd;
import de.oftik.hygs.cmd.project.SaveProjectCmd;
import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.contract.CacheType;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.project.CapabilityInProject;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.ui.ComponentFactory;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.EntityRenderer;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.SaveController;
import de.oftik.hygs.ui.TextFields;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;
import de.oftik.kehys.keijukainen.gui.ListTableModel;
import de.oftik.kehys.kersantti.query.Converters;

public class ProjectForm extends EntityForm<Project> {
	private static final Logger log = Logger.getLogger(ProjectForm.class.getName());

	private final JTextField idField = new JTextField();
	private final JTextField nameField = new JTextField();
	private final JTextArea descriptionArea = new JTextArea(4, 20);
	private final UtilDateModel fromModel = new UtilDateModel();
	private final UtilDateModel toModel = new UtilDateModel();
	private final JDatePicker fromPicker = new JDatePicker(fromModel);
	private final JDatePicker toPicker = new JDatePicker(toModel);
	private final JComboBox<Company> companyBox = new JComboBox<>();
	private final JComboBox<CapabilityWithCategory> capabilityBox = new JComboBox<>();
	private final String[] identifiers = new String[] { I18N.CAPABILITY.label(), I18N.VERSION.label(),
			I18N.WEIGHT.label() };

	private final JTable capabilityTable = new JTable();
	private CompanyCache companyCache;
	private CapabilityCache capabilityCache;

	private AssignedCapabilityDAO assignedCapabilityDAO;
	private final ListTableModel<CapabilityInProject> tableModel = new ListTableModel<CapabilityInProject>(
			Arrays.asList(createDescription(identifiers[0], String.class, CapabilityInProject::getName),
					createDescription(identifiers[1], String.class, CapabilityInProject::getVersion),
					createDescription(identifiers[2], Number.class, CapabilityInProject::getWeight, true))) {

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

	public ProjectForm(SaveController sc, Supplier<CommandBroker> brokerSupplier) {
		this(sc, brokerSupplier, false);
	}

	public ProjectForm(SaveController sc, Supplier<CommandBroker> brokerSupplier, boolean createMode) {
		super(sc, brokerSupplier);
		this.createMode = createMode;
		capabilityTable.setModel(tableModel);
		capabilityTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
		companyBox.setRenderer(new EntityRenderer<>(p -> p.getName()));
		capabilityBox.setRenderer(new EntityRenderer<CapabilityWithCategory>(
				cwc -> cwc.getCategory().getName() + " -> " + cwc.getCapability().getName()
						+ (cwc.getCapability().getVersion() == null ? "" : " " + cwc.getCapability().getVersion())));
		createUI();
		addCapButton.setEnabled(false);
		fromPicker.setEnabled(false);
		toPicker.setEnabled(false);
		capabilityBox.setEnabled(false);
	}

	@Override
	public Command createEntityCommand() {
		return new CreateProjectCmd(nameField.getText(), convertDate(fromModel.getValue()),
				convertDate(toModel.getValue()), (Company) companyBox.getSelectedItem(), descriptionArea.getText(), 0);
	}

	@Override
	public Command saveEntityCommand() {
		return new SaveProjectCmd(idField.getText(), nameField.getText(), convertDate(fromModel.getValue()),
				convertDate(toModel.getValue()), (Company) companyBox.getSelectedItem(), descriptionArea.getText(), 0);
	}

	@Override
	public Command deleteEntityCommand() {
		var prj = new Project();
		prj.setId(idField.getText());
		return new DeleteProjectCmd(prj);
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

	void changeCapabilityWeight(CapabilityInProject cap, int weight) {
		broker().execute(new ChangeWeightCmd(cap, weight));
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
				setSaveable(!TextFields.isBlank(nameField));
			}
		});
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
			broker().execute(new AssignCapabilityCmd(currentProject,
					((CapabilityWithCategory) capabilityBox.getSelectedItem()).getCapability()));
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
		companyBox.setSelectedItem(companyCache.getCompany(t.getCompanyId()));
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
			final List<CapabilityInProject> caps = assignedCapabilityDAO.findByProject(currentProject);
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
	public boolean isNewEntity() {
		return TextFields.isBlank(idField);
	}

	@Override
	public void enableActions(boolean state) {
		addCapButton.setEnabled(state);
		fromPicker.setEnabled(state);
		toPicker.setEnabled(state);
		capabilityBox.setEnabled(state);
	}
}
