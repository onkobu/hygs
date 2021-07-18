package de.oftik.hygs.ui.cap;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.cmd.cap.CreateCapabilityCmd;
import de.oftik.hygs.cmd.cap.DeleteCapabilityCmd;
import de.oftik.hygs.cmd.cap.SaveCapabilityCmd;
import de.oftik.hygs.cmd.cat.CreateCategoryCmd;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.ui.ComponentFactory;
import de.oftik.hygs.ui.GroupedEntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.SaveController;
import de.oftik.hygs.ui.TextFields;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class CapabilityForm extends GroupedEntityForm<Category, Capability> {
	private final JTextField idField = new JTextField();

	private final JTextField nameField = new JTextField();

	private final JTextField versionField = new JTextField();

	private final DefaultComboBoxModel<Category> categoriesModel = new DefaultComboBoxModel<>();
	private final JComboBox<Category> categories = new JComboBox<>(categoriesModel);

	private final Component createCategoryButton;

	static class CategoryRenderer implements ListCellRenderer<Category> {
		private final DefaultListCellRenderer rendererDelegate = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends Category> list, Category value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return value == null ? new JLabel()
					: rendererDelegate.getListCellRendererComponent(list, value.getName(), index, isSelected,
							cellHasFocus);
		}
	}

	public CapabilityForm(SaveController sc, Supplier<CommandBroker> brokerSupplier) {
		super(sc, brokerSupplier);
		categories.setRenderer(new CategoryRenderer());
		categories.setEditable(false);
		categories.setEnabled(false);
		createCategoryButton = ComponentFactory.createButton(I18N.CREATE_CATEGORY, this::createCategory);
		createCategoryButton.setEnabled(false);
		createUI();
		broker().registerListener(new NotificationListener() {
			@Override
			public CommandTarget target() {
				return CommandTargetDefinition.category;
			}

			@Override
			public void onEnqueueError(Notification notification) {
				errorCreatingCategory(notification);
			}

			@Override
			public void onSuccess(Notification notification) {
				categoryCreated(notification);
			}
		});
	}

	private void createUI() {
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		addIDField(idField, gbc);

		addLargeTextField(I18N.CAPABILITY, nameField, gbc.nextRow());
		addShortTextField(I18N.VERSION, versionField, gbc.nextColumn());

		add(I18N.CATEGORY, categories, gbc.nextRow());

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 8));
		buttonPanel.add(createCategoryButton);
		add(buttonPanel, gbc.nextRow().remainderX().end());

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
	}

	@Override
	public void showEntity(Category cat, Capability cap) {
		nameField.setText(cap.getName());
		idField.setText(String.valueOf(cap.getId()));
		versionField.setText(cap.getVersion());
		categories.setSelectedItem(cat);
		categories.setEnabled(true);
	}

	@Override
	public void blank() {
		nameField.setText(null);
		idField.setText(null);
		versionField.setText(null);
		categories.setSelectedIndex(-1);
	}

	public void createCategory(ActionEvent event) {
		final String catName = JOptionPane.showInputDialog(this, I18N.CATEGORY.label());
		if (catName == null || catName.trim().length() == 0) {
			return;
		}
		broker().execute(new CreateCategoryCmd(catName));
	}

	@Override
	protected void setGroups(List<Category> groups) {
		groups.forEach(categoriesModel::addElement);
		categories.setEnabled(true);
	}

	private void errorCreatingCategory(Notification notification) {
		JOptionPane.showMessageDialog(this, notification.result().name(), I18N.DLG_ERROR.title(),
				JOptionPane.ERROR_MESSAGE);
	}

	private void categoryCreated(Notification notification) {
		JOptionPane.showMessageDialog(this, notification.result().name(), I18N.DLG_INFO.title(),
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public Command createEntityCommand() {
		return new CreateCapabilityCmd(nameField.getText(), nullable(versionField.getText()),
				(Category) categories.getSelectedItem());
	}

	@Override
	public Command saveEntityCommand() {
		return new SaveCapabilityCmd(idField.getText(), nameField.getText(), nullable(versionField.getText()),
				(Category) categories.getSelectedItem());
	}

	@Override
	public Command deleteEntityCommand() {
		final var cap = new Capability();
		cap.setId(idField.getText());
		return new DeleteCapabilityCmd(cap);
	}

	@Override
	public void destroy() {
		// clear caches
	}

	@Override
	protected boolean isNewEntity() {
		return TextFields.isBlank(idField);
	}

	@Override
	public void enableActions(boolean state) {
		createCategoryButton.setEnabled(state);
	}
}
