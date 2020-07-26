package de.oftik.hygs.ui;

import static de.oftik.hygs.ui.ComponentFactory.createButton;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.keyhs.kersantti.query.DAO;

/**
 * A panel with a tree-like structure on one side, grouping entities. Next to it
 * a form shows details of a selected entity.
 *
 * @author onkobu
 *
 * @param <G>
 * @param <E>
 */
public abstract class GroupedEntityPanel<G extends Identifiable<G>, E extends Identifiable<E> & MappableToString, F extends GroupedEntityForm<G, E>>
		extends JPanel implements ApplicationContextListener {
	private static final Logger log = Logger.getLogger(GroupedEntityPanel.class.getName());

	private final FilterNode root;
	private final FilterTreeModel treeModel;
	private final JTree tree;
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private final JTextField filterField = ComponentFactory.textField(I18N.SEARCH);
	private final F entityForm;
	private final DAO<G> groupDao;
	private final DAO<E> entityDao;

	private final Map<String, FilterNode> groupMap = new HashMap<>();

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
	protected static final class EntityNotificationListener<G extends Identifiable<G>, E extends Identifiable<E> & MappableToString, F extends GroupedEntityForm<G, E>>
			implements NotificationListener {
		private final CommandTarget target;
		private final GroupedEntityPanel<G, E, F> reference;

		public EntityNotificationListener(CommandTarget target, GroupedEntityPanel<G, E, F> ref) {
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
				log.warning("unhandled notification type " + notification);
			}
		}
	}

	public GroupedEntityPanel(ApplicationContext context, I18N rootTitle, DAO<G> groupDao, DAO<E> entityDao,
			TreeCellRenderer renderer) {
		this.applicationContext = context;
		this.groupDao = groupDao;
		this.entityDao = entityDao;
		this.entityForm = createForm(context::getBroker);
		this.root = new FilterNode(rootTitle.label());
		this.treeModel = new FilterTreeModel(root, true, true);
		this.tree = new JTree(treeModel);
		tree.setCellRenderer(renderer);
		tree.addTreeSelectionListener(this::nodeSelected);
		context.addListener(this);

		this.newButton = createButton(I18N.NEW_ENTITY, this::createEntity);
		this.saveButton = createButton(I18N.SAVE_CHANGES, this::saveEntity);
		this.deleteButton = createButton(I18N.DELETE, this::deleteEntity);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);

		createUI();
		fillTree();
	}

	public void onEntityResurrected(List<String> ids) {
		fillTree();
	}

	public void onEntityDelete(List<String> ids) {
		selectionCleared();
		fillTree();
	}

	public void onEntityUpdate(List<String> ids) {
		fillTree();
	}

	public void onEntityInsert(List<String> ids) {
		fillTree();
	}

	public abstract void createEntity(ActionEvent evt);

	public void saveEntity(ActionEvent evt) {
		entityForm.saveEntity();
	}

	public void deleteEntity(ActionEvent evt) {
		entityForm.deleteEntity();
	}

	@SuppressWarnings({ "PMD.UnusedFormalParameter", "unchecked" })
	private void nodeSelected(TreeSelectionEvent evt) {
		final TreePath sp = tree.getSelectionPath();
		if (sp == null) {
			selectionCleared();
			return;
		}
		final DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) sp.getLastPathComponent();
		if (isGroupNode(selNode)) {
			groupSelected((G) selNode.getUserObject());
			return;
		}

		if (isEntityNode(selNode)) {
			entitySelected((G) ((DefaultMutableTreeNode) selNode.getParent()).getUserObject(),
					(E) selNode.getUserObject());
			return;
		}
		selectionCleared();
	}

	@Override
	public void onEvent(ContextEvent e) {
		switch (e.getEventType()) {
		case RELOAD_DATABASE:
			fillTree();
			break;
		case CLOSED_DATABASE:
			clearTree();
			break;
		}
	}

	protected abstract boolean isEntityNode(DefaultMutableTreeNode node);

	protected abstract boolean isGroupNode(DefaultMutableTreeNode node);

	protected void groupSelected(G g) {
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
	}

	protected void entitySelected(G g, E e) {
		saveButton.setEnabled(true);
		deleteButton.setEnabled(true);
		entityForm.showEntity(g, e);
	}

	protected void selectionCleared() {
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
		entityForm.clearEntity();
	}

	protected abstract List<E> loadForGroup(G g) throws SQLException;

	protected DAO<E> entityDao() {
		return entityDao;
	}

	protected CommandBroker broker() {
		return applicationContext.getBroker();
	}

	private void createUI() {
		setLayout(new BorderLayout());
		final JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		treePanel.add(new JScrollPane(tree), BorderLayout.CENTER);
		treePanel.add(filterField, BorderLayout.NORTH);
		filterField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (filterField.getText().trim().length() < 3) {
					treeModel.setFiltering(false);
					return;
				}
				treeModel.setFiltering(true);
				filterTree(filterField.getText().trim());
			}
		});
		splitPane.setLeftComponent(treePanel);

		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(entityForm, BorderLayout.CENTER);
		centerPanel.add(EntityListPanel.createActionPanel(newButton, saveButton, deleteButton), BorderLayout.SOUTH);

		splitPane.setRightComponent(centerPanel);
		add(splitPane, BorderLayout.CENTER);
	}

	public abstract F createForm(Supplier<CommandBroker> brokerSupplier);

	public GroupedEntityCreateDialog<G, E, F> wrapFormAsCreateDialog() {
		return new GroupedEntityCreateDialog<>(this, createForm(applicationContext::getBroker));
	}

	private void filterTree(String term) {
		treeModel.filter(term);
	}

	protected void refreshGroupIds(Notification notification) {
		notification.getIds().forEach((id) -> {
			try {
				Optional<G> optG = groupDao.findById(id);
				if (optG.isPresent()) {
					addOrReplace(optG.get());
				} else {
					delete(optG.get());
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, "refreshGroupIds", e);
			}
		});
		treeModel.nodeStructureChanged(root);
		entityForm.setGroups(extractGroups());
	}

	@SuppressWarnings("unchecked")
	private List<G> extractGroups() {
		final List<G> groups = new ArrayList<>();
		for (int i = 0; i < root.getChildCount(); i++) {
			groups.add((G) ((DefaultMutableTreeNode) root.getChildAt(i)).getUserObject());
		}
		return groups;
	}

	private void addOrReplace(G g) {
		final DefaultMutableTreeNode existing = groupMap.get(g.getId());
		if (existing == null) {
			registerGroup(toGroupNode(g));
		}
	}

	@SuppressWarnings("PMD.UnusedFormalParameter")
	private void delete(G g) {
		throw new UnsupportedOperationException("Not ready yet");
	}

	private void fillTree() {
		final List<G> groups = new ArrayList<>();
		try {
			groupDao.consumeAll((g) -> {
				final FilterNode tn = toGroupNode(g);
				groups.add(g);
				registerGroup(tn);
				List<E> entities = null;
				try {
					entities = loadForGroup(g);
				} catch (SQLException e) {
					log.throwing(GroupedEntityPanel.class.getSimpleName(), "fillTree", e);
				}
				if (entities == null || entities.isEmpty()) {
					return;
				}
				entities.stream().map(GroupedEntityPanel::toNode).forEach(tn::add);
			});
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		treeModel.nodeStructureChanged(root);
		entityForm.setGroups(groups);
	}

	private void clearTree() {
		root.removeAllChildren();
		treeModel.nodeStructureChanged(root);
		entityForm.setGroups(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	protected FilterNode registerGroup(final FilterNode tn) {
		root.add(tn);
		return groupMap.put(((G) tn.getUserObject()).getId(), tn);
	}

	protected FilterNode toGroupNode(G g) {
		final FilterNode tn = new FilterNode();
		tn.setUserObject(g);
		return tn;
	}

	private static FilterNode toNode(Object obj) {
		final FilterNode tn = new FilterNode();
		tn.setUserObject(obj);
		return tn;
	}
}
