package de.oftik.hygs.ui;

import static de.oftik.hygs.ui.ComponentFactory.createButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

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
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Table;
import de.oftik.kehys.kersantti.query.DAO;
import de.oftik.kehys.kersantti.query.QueryOperators;

/**
 * A panel that contains a list of entities and a form next to it. Upon
 * selection of an entity in the list the form displays all details.
 *
 * @author onkobu
 *
 * @param <T>
 */
public abstract class EntityListPanel<I extends de.oftik.kehys.kersantti.Identifiable, T extends Table<I>, F extends EntityForm<I>>
		extends JPanel implements ApplicationContextListener, SaveController {
	private static final Logger log = Logger.getLogger(EntityListPanel.class.getName());

	private final DefaultListModel<I> listModel = new DefaultListModel<>();
	private final JList<I> entityList = new JList<>(listModel);
	private final F entityForm;
	private final DAO<I> dao;
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private final ApplicationContext applicationContext;
	private final JButton newButton;
	private final JButton saveButton;
	private final JButton deleteButton;

	private final Column<I> deleteColumn;

	private final Column<I> orderByColumn;

	/**
	 * Use this listener to bind to this class' default behavior upon notification.
	 *
	 * @author onkobu
	 *
	 * @param <T>
	 */
	protected static final class EntityNotificationListener<I extends de.oftik.kehys.kersantti.Identifiable, T extends Table<I>, F extends EntityForm<I>>
			implements NotificationListener {
		private final CommandTarget target;
		private final EntityListPanel<I, T, F> reference;

		public EntityNotificationListener(CommandTarget target, EntityListPanel<I, T, F> ref) {
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
			case DELETE: // both events remove the entity from the panel
			case TRASHED:
				reference.onEntityDelete(notification.getIds());
				break;
			case RESURRECT:
				reference.onEntityResurrected(notification.getIds());
				break;
			default:
				log.warning("unhandled notification type " + notification);
			}
		}
	}

	protected static final class SubElementNotificationListener implements NotificationListener {
		private final CommandTarget target;
		private final Consumer<Notification> successConsumer;
		private final Consumer<Notification> errorConsumer;

		public SubElementNotificationListener(CommandTarget target, Consumer<Notification> successConsumer,
				Consumer<Notification> errorConsumer) {
			super();
			this.target = target;
			this.successConsumer = successConsumer;
			this.errorConsumer = errorConsumer;
		}

		@Override
		public CommandTarget target() {
			return target;
		}

		@Override
		public void onEnqueueError(Notification notification) {
			errorConsumer.accept(notification);
		}

		@Override
		public void onSuccess(Notification notification) {
			successConsumer.accept(notification);
		}
	}

	protected static final class AnyNotificationListener<N extends Notification> implements NotificationListener {
		private final CommandTarget target;

		private final Consumer<N> assignmentConsumer;

		private final List<NotificationType> notificationTypes;

		public AnyNotificationListener(CommandTarget target, Consumer<N> assignmentConsumer, NotificationType... nt) {
			super();
			this.notificationTypes = Arrays.asList(nt);
			this.target = target;
			this.assignmentConsumer = assignmentConsumer;
		}

		@Override
		public CommandTarget target() {
			return target;
		}

		@Override
		public void onEnqueueError(Notification notification) {
			// someone else will handle this
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(Notification notification) {
			if (!notificationTypes.contains(notification.type())) {
				return;
			}
			assignmentConsumer.accept((N) notification);
		}
	}

	public EntityListPanel(ApplicationContext context, DAO<I> dao, ListCellRenderer<I> cellRenderer,
			Column<I> deleteColumn, Column<I> orderByColumn) {
		this.deleteColumn = deleteColumn;
		this.orderByColumn = orderByColumn;
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
		newButton.setEnabled(false);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
		createUI();
		fillList();
	}

	public abstract F createForm(Supplier<CommandBroker> brokerSupplier);

	public final void createEntity(ActionEvent evt) {
		entityForm.blank();
	}

	public void onEntityInsert(List<String> ids) {
		try {
			getDAO().findById(ids.get(0)).ifPresent(listModel::addElement);
			selectById(ids.get(0));
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void onEntityUpdate(List<String> ids) {
		try {
			getDAO().findById(ids.get(0)).ifPresent(e -> {
				for (int i = 0; i < listModel.getSize(); i++) {
					if (listModel.get(i).getId().equals(e.getId())) {
						listModel.set(i, e);
						break;
					}
				}
			});
			selectById(ids.get(0));
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void onEntityDelete(List<String> ids) {
		for (int i = 0; i < listModel.getSize(); i++) {
			if (listModel.get(i).getId().equals(ids.get(0))) {
				listModel.remove(i);
				break;
			}
		}
	}

	public void onEntityResurrected(List<String> ids) {
		try {
			getDAO().findById(ids.get(0)).ifPresent(listModel::addElement);
			selectById(ids.get(0));
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void selectById(String id) {
		for (int i = 0; i < listModel.getSize(); i++) {
			if (listModel.get(i).getId().equals(id)) {
				entityList.setSelectedValue(listModel.get(i), true);
				break;
			}
		}
	}

	public void deleteSelection() {

	}

	public void saveEntity(ActionEvent evt) {
		entityForm.createOrSaveEntity();
	}

	public void deleteEntity(ActionEvent evt) {
		entityForm.deleteEntity();
	}

	protected CommandBroker broker() {
		return applicationContext.getBroker();
	}

	protected F getForm() {
		return entityForm;
	}

	static final JPanel createActionPanel(JButton newButton, JButton saveButton, JButton deleteButton) {
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		panel.add(newButton);
		panel.add(saveButton);
		panel.add(deleteButton);
		return panel;
	}

	private void showEntity(I t) {
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
		centerPanel.add(createActionPanel(newButton, saveButton, deleteButton), BorderLayout.SOUTH);
		splitPane.setRightComponent(centerPanel);
		add(splitPane, BorderLayout.CENTER);
	}

	private void fillList() {
		try {
			getDAO().consumeWhere(QueryOperators.columnIs(deleteColumn, false),
					Collections.singletonList(orderByColumn), listModel::addElement);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private void clearList() {
		listModel.clear();
	}

	@Override
	public void onEvent(ContextEvent e) {
		switch (e.getEventType()) {
		case RELOAD_DATABASE:
			clearList();
			fillList();
			enableActions(true);
			break;
		case CLOSED_DATABASE:
			clearList();
			enableActions(false);
			break;
		}
	}

	private void enableActions(boolean state) {
		newButton.setEnabled(state);
		entityForm.enableActions(state);
	}

	protected DAO<I> getDAO() {
		return dao;
	}

	@Override
	public void setSaveEnabled(boolean state) {
		saveButton.setEnabled(state);
	}
}
