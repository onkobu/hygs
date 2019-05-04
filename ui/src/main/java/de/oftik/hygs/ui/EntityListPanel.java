package de.oftik.hygs.ui;

import static de.oftik.hygs.ui.ComponentFactory.createButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.query.DAO;

/**
 * A panel that contains a list of entities and a form next to it. Upon
 * selection of an entity in the list the form displays all details.
 * 
 * @author onkobu
 *
 * @param <T>
 */
public abstract class EntityListPanel<T extends Identifiable> extends JPanel implements ApplicationContextListener {
	private final DefaultListModel<T> listModel = new DefaultListModel<>();
	private final JList<T> entityList = new JList<>(listModel);
	private final EntityForm<T> entityForm;
	private final DAO<T> dao;
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private final ApplicationContext applicationContext;
	private final JButton newButton;
	private final JButton saveButton;
	private final JButton deleteButton;

	/**
	 * Use this listener to bind to this class' default behavior upon notification.
	 * 
	 * @author onkobu
	 *
	 * @param <T>
	 */
	protected static final class EntityNotificationListener<T extends Identifiable> implements NotificationListener {
		private final CommandTarget target;
		private final EntityListPanel<T> reference;

		public EntityNotificationListener(CommandTarget target, EntityListPanel<T> ref) {
			this.target = target;
			this.reference = ref;
		}

		@Override
		public CommandTarget target() {
			return target;
		}

		@Override
		public void onEnqueueError(Notification notification) {
			// FormPanel will handle this
		}

		@Override
		public void onSuccess(Notification notification) {
			switch (notification.type()) {
			case INSERT:
				reference.onEntityInsert(notification.getIds());
				break;
			case UPDATE:
				reference.onEntityUpdate(notification.getIds());
				break;
			case DELETE:
				reference.onEntityDelete(notification.getIds());
				break;
			case RESURRECT:
				reference.onEntityResurrected(notification.getIds());
				break;
			default:

			}
		}
	}

	public EntityListPanel(ApplicationContext context, DAO<T> dao, ListCellRenderer<T> cellRenderer) {
		this.applicationContext = context;
		this.entityForm = createForm(context::getBroker);
		this.dao = dao;
		entityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityList.setCellRenderer(cellRenderer);
		entityList.addListSelectionListener((evt) -> {
			showEntity(entityList.getSelectedValue());
		});
		context.addListener(this);
		newButton = createButton(I18N.NEW_ENTITY, this::createEntity);
		saveButton = createButton(I18N.SAVE_CHANGES, this::saveEntity);
		deleteButton = createButton(I18N.DELETE, this::deleteEntity);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
		createUI();
		fillList();
	}

	public abstract EntityForm<T> createForm(Supplier<CommandBroker> brokerSupplier);

	public abstract void createEntity(ActionEvent evt);

	public void onEntityInsert(List<Long> ids) {
		fillList();
		selectById(ids.get(0));
	}

	public void onEntityUpdate(List<Long> ids) {
		fillList();
		selectById(ids.get(0));
	}

	public void onEntityDelete(List<Long> ids) {
		fillList();
		deleteSelection();
	}

	public void onEntityResurrected(List<Long> ids) {
		fillList();
		deleteSelection();
	}

	public void selectById(Long id) {
		for (int i = 0; i < listModel.getSize(); i++) {
			if (listModel.get(i).getId() == id.longValue()) {
				entityList.setSelectedValue(listModel.get(i), true);
				break;
			}
		}
	}

	public void deleteSelection() {

	}

	public void saveEntity(ActionEvent evt) {
		entityForm.saveEntity();
	}

	public void deleteEntity(ActionEvent evt) {
		entityForm.deleteEntity();
	}

	public EntityCreateDialog<T> wrapFormAsCreateDialog() {
		return new EntityCreateDialog<>(this, createForm(applicationContext::getBroker));
	}

	public EntityCreateDialog<T> wrapFormAsSaveDialog() {
		return new EntityCreateDialog<>(this, createForm(applicationContext::getBroker));
	}

	protected CommandBroker broker() {
		return applicationContext.getBroker();
	}

	protected EntityForm<T> getForm() {
		return entityForm;
	}

	protected final JPanel createActionPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		panel.add(newButton);
		panel.add(saveButton);
		panel.add(deleteButton);
		return panel;
	}

	private void showEntity(T t) {
		// Upon clear or remove removing of selection yields null entities
		if (t == null) {
			saveButton.setEnabled(false);
			deleteButton.setEnabled(false);
			entityForm.blank();
			return;
		}
		entityForm.showEntity(t);
		saveButton.setEnabled(true);
		deleteButton.setEnabled(true);
	}

	private void createUI() {
		setLayout(new BorderLayout());
		splitPane.setLeftComponent(new JScrollPane(entityList));
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(entityForm, BorderLayout.CENTER);
		centerPanel.add(createActionPanel(), BorderLayout.SOUTH);
		splitPane.setRightComponent(centerPanel);
		add(splitPane, BorderLayout.CENTER);
	}

	private void fillList() {
		listModel.clear();
		try {
			getDAO().consumeAll(listModel::addElement);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void onEvent(ContextEvent e) {
		switch (e.getEventType()) {
		case RELOAD_DATABASE:
			fillList();
			break;
		}
	}

	protected DAO<T> getDAO() {
		return dao;
	}
}
