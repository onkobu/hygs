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
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.query.DAO;

/**
 * A panel with a tree-like structure on one side, grouping entities. Next to it
 * a form shows details of a selected entity.
 *
 * @author onkobu
 *
 * @param <G>
 * @param <E>
 */
public abstract class GroupedEntityPanel<G extends de.oftik.kehys.kersantti.Identifiable, E extends de.oftik.kehys.kersantti.Identifiable, F extends GroupedEntityForm<G, E>, D extends DAO<E>>
		extends JPanel implements ApplicationContextListener, SaveController {
	private static final Logger log = Logger.getLogger(GroupedEntityPanel.class.getName());

	private final FilterNode root;
	private final FilterTreeModel treeModel;
	private final JTree tree;
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private final JTextField filterField = ComponentFactory.textField(I18N.SEARCH);
	private final F entityForm;
	private final DAO<G> groupDao;
	private final D entityDao;

	private final Map<String, FilterNode> groupMap = new HashMap<>();

	private final ApplicationContext applicationContext;

	private final JButton newButton;

	private final JButton saveButton;

	private final JButton deleteButton;

	private final List<Column<G>> groupOrderColumns;

	/**
	 * Use this listener to bind to this class' default behavior upon notification.
	 *
	 * @author onkobu
	 *
	 * @param <T>
	 */
	protected static final class EntityNotificationListener<G extends de.oftik.kehys.kersantti.Identifiable, E extends de.oftik.kehys.kersantti.Identifiable, F extends GroupedEntityForm<G, E>, D extends DAO<E>>
			implements NotificationListener {
		private final CommandTarget target;
		private final GroupedEntityPanel<G, E, F, D> reference;

		public EntityNotificationListener(CommandTarget target, GroupedEntityPanel<G, E, F, D> ref) {
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
			case TRASHED:
			case DELETE:
				reference.onEntityDelete(notification.getIds());
				break;
			case RESURRECT:
				reference.onEntityResurrected(notification.getIds());
				break;
			default:
				log.warning("unhandled notification type " + notification.type() + ", target " + notification.target()
						+ " for ID(s) " + notification.getIds());
			}
		}
	}

	public GroupedEntityPanel(ApplicationContext context, I18N rootTitle, DAO<G> groupDao, D entityDao,
			TreeCellRenderer renderer, Column<G> groupOrderColumn) {
		this.applicationContext = context;
		this.groupDao = groupDao;
		this.entityDao = entityDao;
		this.groupOrderColumns = Collections.singletonList(groupOrderColumn);
		this.entityForm = createForm(context::getBroker);
		this.root = new FilterNode(rootTitle.label(), true, true);
		this.treeModel = new FilterTreeModel(root, true, true);
		this.tree = new JTree(treeModel);
		tree.setCellRenderer(renderer);
		tree.addTreeSelectionListener(this::nodeSelected);
		context.addListener(this);

		this.newButton = createButton(I18N.NEW_ENTITY, this::createEntity);
		this.saveButton = createButton(I18N.SAVE_CHANGES, this::saveEntity);
		this.deleteButton = createButton(I18N.DELETE, this::deleteEntity);
		newButton.setEnabled(false);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);

		createUI();
		fillTree();
	}

	public void onEntityResurrected(List<String> ids) {
		// TODO: same as insert
		fillTree();
	}

	public void onEntityDelete(List<String> ids) {
		selectionCleared();
		// TODO: only remove the given entities
		fillTree();
	}

	public void onEntityUpdate(List<String> ids) {
		// TODO: only mix in the entities updated attributes
		fillTree();
	}

	public void onEntityInsert(List<String> ids) {
		// TODO: only insert in the new entities
		fillTree();
	}

	public final void createEntity(ActionEvent evt) {
		entityForm.blank();
	}

	public final void saveEntity(ActionEvent evt) {
		entityForm.createOrSaveEntity();
	}

	public final void deleteEntity(ActionEvent evt) {
		entityForm.deleteEntity();
	}

	@Override
	public void setSaveEnabled(boolean state) {
		saveButton.setEnabled(state);
	}

	@SuppressWarnings({ "PMD.UnusedFormalParameter", "unchecked" })
	private void nodeSelected(TreeSelectionEvent evt) {
		final TreePath sp = tree.getSelectionPath();
		if (sp == null) {
			selectionCleared();
			return;
		}
		final FilterNode selNode = (FilterNode) sp.getLastPathComponent();
		if (isGroupNode(selNode)) {
			groupSelected(((IdentifiableBinding<G>) selNode.getUserObject()).getIdentifiable());
			return;
		}

		if (isEntityNode(selNode)) {
			entitySelected(((IdentifiableBinding<G>) ((DefaultMutableTreeNode) selNode.getParent()).getUserObject())
					.getIdentifiable(), ((IdentifiableBinding<E>) selNode.getUserObject()).getIdentifiable());
			return;
		}
		selectionCleared();
	}

	@Override
	public void onEvent(ContextEvent e) {
		switch (e.getEventType()) {
		case RELOAD_DATABASE:
			fillTree();
			enableActions(true);
			break;
		case CLOSED_DATABASE:
			clearTree();
			enableActions(false);
			break;
		}
	}

	protected abstract boolean isEntityNode(FilterNode node);

	protected abstract boolean isGroupNode(FilterNode node);

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
		entityForm.blank();
	}

	protected abstract List<E> loadForGroup(G g) throws SQLException;

	protected D entityDao() {
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

	public abstract IdentifiableBinding<E> bind(E e);

	public abstract IdentifiableBinding<G> bindGroup(G g);

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
	protected List<G> extractGroups() {
		final List<G> groups = new ArrayList<>();
		for (int i = 0; i < root.getChildCount(); i++) {
			groups.add(((IdentifiableBinding<G>) ((FilterNode) root.getChildAt(i)).getUserObject()).getIdentifiable());
		}
		return groups;
	}

	private FilterNode addOrReplace(G g) {
		final FilterNode existing = groupMap.get(g.getId());
		if (existing == null) {
			return registerGroup(toGroupNode(g));
		} else if (existing.getBinding().getIdentifiable() != g) {
			existing.setUserObject(bindGroup(g));
		}
		return existing;
	}

	@SuppressWarnings("PMD.UnusedFormalParameter")
	private void delete(G g) {
		throw new UnsupportedOperationException("Not ready yet");
	}

	private void fillTree() {
		try {
			groupDao.consumeAll((g) -> {
				final FilterNode groupNode = addOrReplace(g);
				List<E> entities = null;
				try {
					entities = loadForGroup(g);
				} catch (SQLException e) {
					log.throwing(GroupedEntityPanel.class.getSimpleName(), "fillTree", e);
				}
				if (entities == null || entities.isEmpty()) {
					return;
				}
				entities.stream().forEach(e -> {
					final var binding = bind(e);
					groupNode.addOrReplace(binding, () -> toNode(binding));
				});
			}, groupOrderColumns);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		treeModel.nodeStructureChanged(root);
		entityForm.setGroups(extractGroups());
	}

	private void clearTree() {
		root.removeAllChildren();
		treeModel.nodeStructureChanged(root);
		entityForm.setGroups(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	protected FilterNode registerGroup(final FilterNode tn) {
		root.add(tn);
		groupMap.put(((IdentifiableBinding<G>) tn.getUserObject()).getId(), tn);
		return tn;
	}

	protected FilterNode toGroupNode(G g) {
		final FilterNode tn = new FilterNode();
		final IdentifiableBinding<G> gBinding = bindGroup(g);
		tn.setUserObject(gBinding);
		return tn;
	}

	private static FilterNode toNode(Object obj) {
		final FilterNode tn = new FilterNode();
		tn.setUserObject(obj);
		return tn;
	}

	private void enableActions(boolean state) {
		newButton.setEnabled(state);
		entityForm.enableActions(state);
	}

}
