package de.oftik.hygs.ui;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import de.oftik.hygs.ui.orm.DAO;

public abstract class EntityListPanel<T> extends JPanel {
	private final DefaultListModel<T> listModel = new DefaultListModel<>();
	private final JList<T> entityList = new JList<>(listModel);
	private final EntityForm<T> entityForm;
	private final ApplicationContext applicationContext;
	private final DAO<T> dao;

	public EntityListPanel(ApplicationContext applicationContext, DAO<T> dao, EntityForm<T> form,
			ListCellRenderer<T> cellRenderer) {
		this.applicationContext = applicationContext;
		this.entityForm = form;
		this.dao = dao;
		entityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityList.setCellRenderer(cellRenderer);
		entityList.addListSelectionListener((evt) -> {
			showEntity(entityList.getSelectedValue());
		});
		createUI();
		fillList();
	}

	protected EntityForm<T> getForm() {
		return entityForm;
	}

	private void showEntity(T t) {
		entityForm.showEntity(t);
	}

	private void createUI() {
		setLayout(new BorderLayout());
		add(new JScrollPane(entityList), BorderLayout.WEST);
		add(entityForm, BorderLayout.CENTER);
	}

	private void fillList() {
		final List<T> all;
		try {
			all = getDAO().findAll();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		all.stream().forEach(listModel::addElement);
	}

	protected DAO<T> getDAO() {
		return dao;
	}
}
