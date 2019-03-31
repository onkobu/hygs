package de.oftik.hygs.ui.cap;

import java.awt.Component;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.cmd.cat.CreateCategoryCmd;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.ui.GroupedEntityForm;
import de.oftik.hygs.ui.I18N;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class CapabilityForm extends GroupedEntityForm<Category, Capability> {
	private final JTextField idField = new JTextField();

	private final JTextField nameField = new JTextField();

	private final JTextField versionField = new JTextField();

	private final DefaultComboBoxModel<Category> categoriesModel = new DefaultComboBoxModel<>();
	private final JComboBox<Category> categories = new JComboBox<>(categoriesModel);

	static class CategoryRenderer implements ListCellRenderer<Category> {
		private final DefaultListCellRenderer rendererDelegate = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends Category> list, Category value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return rendererDelegate.getListCellRendererComponent(list, value.getName(), index, isSelected,
					cellHasFocus);
		}
	}

	public CapabilityForm(Supplier<CommandBroker> brokerSupplier) {
		super(brokerSupplier);
		categories.setRenderer(new CategoryRenderer());
		categories.setEditable(false);
		categories.setEnabled(false);
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

		addButton(I18N.CREATE_CATEGORY, this::createCategory, gbc.nextRow());
	}

	@Override
	public void showEntity(Category cat, Capability cap) {
		nameField.setText(cap.getName());
		idField.setText(String.valueOf(cap.getId()));
		versionField.setText(cap.getVersion());
		categories.setSelectedItem(cat);
	}

	@Override
	public void clearEntity() {
		nameField.setText("");
		idField.setText("");
		versionField.setText("");
		categories.setSelectedIndex(-1);
	}

	public void createCategory() {
		final String catName = JOptionPane.showInputDialog(this, I18N.CATEGORY.label());
		if (catName == null || catName.trim().length() == 0) {
			return;
		}
		broker().execute(new CreateCategoryCmd(catName));
	}

	@Override
	protected void setGroups(List<Category> groups) {
		groups.forEach(categoriesModel::addElement);
	}

	private void errorCreatingCategory(Notification notification) {
		JOptionPane.showMessageDialog(this, notification.result().name(), I18N.DLG_ERROR.title(),
				JOptionPane.ERROR_MESSAGE);
	}

	private void categoryCreated(Notification notification) {
		JOptionPane.showMessageDialog(this, notification.result().name(), I18N.DLG_INFO.title(),
				JOptionPane.INFORMATION_MESSAGE);
	}
}
