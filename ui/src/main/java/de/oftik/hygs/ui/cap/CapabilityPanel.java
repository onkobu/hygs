package de.oftik.hygs.ui.cap;

import java.awt.Component;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.GroupedEntityPanel;

public class CapabilityPanel extends GroupedEntityPanel<Category, Capability> {
	static class CapabilityTreeCellRenderer implements TreeCellRenderer {
		private final DefaultTreeCellRenderer rendererDelegate = new DefaultTreeCellRenderer();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getUserObject() instanceof Category) {
				final Category cat = (Category) node.getUserObject();
				return rendererDelegate.getTreeCellRendererComponent(tree, cat.getName(), selected, expanded, false,
						row, hasFocus);
			}

			if (node.getUserObject() instanceof Capability) {
				final Capability cap = (Capability) node.getUserObject();
				if (cap.getVersion() != null) {
					return rendererDelegate.getTreeCellRendererComponent(tree, cap.getName() + " " + cap.getVersion(),
							selected, expanded, true, row, hasFocus);
				}
				return rendererDelegate.getTreeCellRendererComponent(tree, cap.getName(), selected, expanded, true, row,
						hasFocus);
			}
			return rendererDelegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}

	}

	public CapabilityPanel(ApplicationContext context) {
		super(context, new CategoryDAO(context), new CapabilityDAO(context), new CapabilityForm(),
				new CapabilityTreeCellRenderer());
	}

	@Override
	protected boolean isEntityNode(DefaultMutableTreeNode node) {
		return node.getUserObject() != null && node.getUserObject() instanceof Capability;
	}

	@Override
	protected boolean isGroupNode(DefaultMutableTreeNode node) {
		return node.getUserObject() != null && node.getUserObject() instanceof Category;
	}

	@Override
	protected List<Capability> loadForGroup(Category g) throws SQLException {
		return ((CapabilityDAO) entityDao()).findByCategory(g);
	}

}
