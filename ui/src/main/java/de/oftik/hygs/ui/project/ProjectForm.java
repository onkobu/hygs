package de.oftik.hygs.ui.project;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.orm.Converters;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

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

	private final DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
	private final String[] identifiers = new String[] { I18N.CAPABILITY.label(), I18N.VERSION.label(),
			I18N.WEIGHT.label() };
	private final DefaultTableModel tableModel = new DefaultTableModel(identifiers, 3);
	private final JTable capabilityTable = new JTable(tableModel, columnModel);
	private CompanyCache companyCache;
	private AssignedCapabilityDAO assignedCapabilityDAO;

	static class CapabilityNameRenderer implements TableCellRenderer {
		private final DefaultTableCellRenderer rendererDelegate = new DefaultTableCellRenderer();

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus,
				int row, int column) {
			if (value == null) {
				return rendererDelegate.getTableCellRendererComponent(table, value, selected, hasFocus, row, column);
			}
			return rendererDelegate.getTableCellRendererComponent(table, ((AssignedCapability) value).getName(),
					selected, hasFocus, row, column);
		}
	}

	static class CapabilityVersionRenderer implements TableCellRenderer {
		private final DefaultTableCellRenderer rendererDelegate = new DefaultTableCellRenderer();

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus,
				int row, int column) {
			if (value == null) {
				return rendererDelegate.getTableCellRendererComponent(table, value, selected, hasFocus, row, column);
			}
			return rendererDelegate.getTableCellRendererComponent(table, ((AssignedCapability) value).getVersion(),
					selected, hasFocus, row, column);
		}
	}

	static class CapabilityWeightRenderer implements TableCellRenderer {
		private final DefaultTableCellRenderer rendererDelegate = new DefaultTableCellRenderer();

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus,
				int row, int column) {
			if (value == null) {
				return rendererDelegate.getTableCellRendererComponent(table, value, selected, hasFocus, row, column);
			}
			return rendererDelegate.getTableCellRendererComponent(table, ((AssignedCapability) value).getWeight(),
					selected, hasFocus, row, column);
		}
	}

	public ProjectForm() {
		columnModel.addColumn(new TableColumn(0, 30, new CapabilityNameRenderer(), null));
		columnModel.getColumn(0).setHeaderValue(identifiers[0]);
		columnModel.addColumn(new TableColumn(0, 10, new CapabilityVersionRenderer(), null));
		columnModel.getColumn(1).setHeaderValue(identifiers[1]);
		columnModel.addColumn(new TableColumn(0, 10, new CapabilityWeightRenderer(), null));
		columnModel.getColumn(2).setHeaderValue(identifiers[2]);
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
				tableModel.setRowCount(0);
				return;
			}
			tableModel.setRowCount(caps.size());
			final List<Object[]> capsLst = caps.stream().map((cap) -> new Object[] { cap, cap, cap })
					.collect(Collectors.toList());
			tableModel.setDataVector(capsLst.toArray(new Object[capsLst.size()][]), identifiers);
		} catch (SQLException e) {
			log.throwing(getClass().getSimpleName(), "showEntity", e);
		}
	}
}
