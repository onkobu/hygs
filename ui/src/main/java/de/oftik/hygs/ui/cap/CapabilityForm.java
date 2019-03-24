package de.oftik.hygs.ui.cap;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

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

	public CapabilityForm() {
		categories.setRenderer(new CategoryRenderer());
		categories.setEditable(false);
		categories.setEnabled(false);
		createUI();
	}

	private void createUI() {
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		addIDField(idField, gbc);

		addLargeTextField(I18N.CAPABILITY, nameField, gbc.nextRow());
		addShortTextField(I18N.VERSION, versionField, gbc.nextColumn());

		add(I18N.CATEGORY, categories, gbc.nextRow());
	}

	@Override
	public void showEntity(Category cat, Capability cap) {
		nameField.setText(cap.getName());
		idField.setText(String.valueOf(cap.getId()));
		versionField.setText(cap.getVersion());
		categories.setSelectedItem(cat);
	}

	@Override
	protected void setGroups(List<Category> groups) {
		groups.forEach(categoriesModel::addElement);
	}
}
