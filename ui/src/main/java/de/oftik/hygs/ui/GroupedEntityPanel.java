package de.oftik.hygs.ui;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Identifiable;

public abstract class GroupedEntityPanel<G extends Identifiable, E> extends JPanel {
	private static final Logger log = Logger.getLogger(GroupedEntityPanel.class.getName());

	private final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
	private final DefaultTreeModel treeModel = new DefaultTreeModel(root);
	private final JTree tree = new JTree(treeModel);
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private final GroupedEntityForm<G, E> entityForm;
	private final ApplicationContext applicationContext;
	private final DAO<G> groupDao;
	private final DAO<E> entityDao;

	private final Map<Long, DefaultMutableTreeNode> groupMap = new HashMap<>();

	public GroupedEntityPanel(ApplicationContext context, DAO<G> groupDao, DAO<E> entityDao,
			GroupedEntityForm<G, E> entityForm, TreeCellRenderer renderer) {
		this.applicationContext = context;
		this.groupDao = groupDao;
		this.entityDao = entityDao;
		this.entityForm = entityForm;
		tree.setCellRenderer(renderer);
		tree.addTreeSelectionListener(this::nodeSelected);
		createUI();
		fillTree();
	}

	private void nodeSelected(TreeSelectionEvent evt) {
		final TreePath sp = tree.getSelectionPath();
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

	protected abstract boolean isEntityNode(DefaultMutableTreeNode node);

	protected abstract boolean isGroupNode(DefaultMutableTreeNode node);

	protected void groupSelected(G g) {
	}

	protected void entitySelected(G g, E e) {
		entityForm.showEntity(g, e);
	}

	protected abstract List<E> loadForGroup(G g) throws SQLException;

	protected DAO<E> entityDao() {
		return entityDao;
	}

	private void createUI() {
		setLayout(new BorderLayout());
		splitPane.setLeftComponent(new JScrollPane(tree));
		splitPane.setRightComponent(entityForm);
		add(splitPane, BorderLayout.CENTER);
	}

	private void fillTree() {
		final List<G> groups = new ArrayList<>();
		try {
			groupDao.consumeAll((g) -> {
				final DefaultMutableTreeNode tn = new DefaultMutableTreeNode();
				tn.setUserObject(g);
				groups.add(g);
				root.add(tn);
				List<E> entities = null;
				try {
					entities = loadForGroup(g);
				} catch (SQLException e) {
					log.throwing(GroupedEntityPanel.class.getSimpleName(), "fillTree", e);
				}
				groupMap.put(g.getId(), tn);
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

	private static DefaultMutableTreeNode toNode(Object obj) {
		final DefaultMutableTreeNode tn = new DefaultMutableTreeNode();
		tn.setUserObject(obj);
		return tn;
	}
}
