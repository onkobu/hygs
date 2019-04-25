package de.oftik.hygs.ui;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Identifiable;

/**
 * A panel with a tree-like structure on one side, grouping entities. Next to it
 * a form shows details of a selected entity.
 * 
 * @author onkobu
 *
 * @param <G>
 * @param <E>
 */
public abstract class GroupedEntityPanel<G extends Identifiable, E> extends JPanel
		implements ApplicationContextListener {
	private static final Logger log = Logger.getLogger(GroupedEntityPanel.class.getName());

	private final DefaultMutableTreeNode root;
	private final DefaultTreeModel treeModel;
	private final JTree tree;
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private final GroupedEntityForm<G, E> entityForm;
	private final DAO<G> groupDao;
	private final DAO<E> entityDao;

	private final Map<Long, DefaultMutableTreeNode> groupMap = new HashMap<>();

	private final ApplicationContext applicationContext;

	public GroupedEntityPanel(ApplicationContext context, I18N rootTitle, DAO<G> groupDao, DAO<E> entityDao,
			GroupedEntityForm<G, E> entityForm, TreeCellRenderer renderer) {
		this.applicationContext = context;
		this.groupDao = groupDao;
		this.entityDao = entityDao;
		this.entityForm = entityForm;
		this.root = new DefaultMutableTreeNode(rootTitle.label());
		this.treeModel = new DefaultTreeModel(root);
		this.tree = new JTree(treeModel);
		tree.setCellRenderer(renderer);
		tree.addTreeSelectionListener(this::nodeSelected);
		context.addListener(this);
		createUI();
		fillTree();
	}

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
		}
	}

	@Override
	public void onEvent(ContextEvent e) {
		switch (e.getEventType()) {
		case RELOAD_DATABASE:
			fillTree();
			break;
		}
	}

	protected abstract boolean isEntityNode(DefaultMutableTreeNode node);

	protected abstract boolean isGroupNode(DefaultMutableTreeNode node);

	protected void groupSelected(G g) {
	}

	protected void entitySelected(G g, E e) {
		entityForm.showEntity(g, e);
	}

	protected void selectionCleared() {
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
		splitPane.setLeftComponent(new JScrollPane(tree));
		splitPane.setRightComponent(entityForm);
		add(splitPane, BorderLayout.CENTER);
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

	private void delete(G g) {
		throw new UnsupportedOperationException("Not ready yet");
	}

	private void fillTree() {
		final List<G> groups = new ArrayList<>();
		try {
			groupDao.consumeAll((g) -> {
				final DefaultMutableTreeNode tn = toGroupNode(g);
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

	protected DefaultMutableTreeNode registerGroup(final DefaultMutableTreeNode tn) {
		root.add(tn);
		return groupMap.put(((G) tn.getUserObject()).getId(), tn);
	}

	protected DefaultMutableTreeNode toGroupNode(G g) {
		final DefaultMutableTreeNode tn = new DefaultMutableTreeNode();
		tn.setUserObject(g);
		return tn;
	}

	private static DefaultMutableTreeNode toNode(Object obj) {
		final DefaultMutableTreeNode tn = new DefaultMutableTreeNode();
		tn.setUserObject(obj);
		return tn;
	}
}
