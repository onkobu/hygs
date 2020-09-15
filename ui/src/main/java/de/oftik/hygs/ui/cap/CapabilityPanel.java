package de.oftik.hygs.ui.cap;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationListener;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.cap.CapabilityDAO;
import de.oftik.hygs.query.cap.Category;
import de.oftik.hygs.query.cap.CategoryDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.GroupedEntityCreateDialog;
import de.oftik.hygs.ui.GroupedEntityPanel;
import de.oftik.hygs.ui.I18N;

public class CapabilityPanel extends GroupedEntityPanel<Category, Capability, CapabilityForm> {
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
		super(context, I18N.CATEGORY, new CategoryDAO(context), new CapabilityDAO(context),
				new CapabilityTreeCellRenderer());
		broker().registerListener(new NotificationListener() {
			@Override
			public CommandTarget target() {
				return CommandTargetDefinition.category;
			}

			@Override
			public void onEnqueueError(Notification notification) {
				// FormPanel will handle this
			}

			@Override
			public void onSuccess(Notification notification) {
				refreshGroupIds(notification);
			}
		});
		broker().registerListener(new EntityNotificationListener<>(CommandTargetDefinition.capability, this));
	}

	@Override
	public CapabilityForm createForm(Supplier<CommandBroker> brokerSupplier) {
		return new CapabilityForm(brokerSupplier);
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

	@Override
	public void createEntity(ActionEvent evt) {
		final GroupedEntityCreateDialog<Category, Capability, CapabilityForm> dlg = wrapFormAsCreateDialog();
		dlg.getForm().setGroups(extractGroups());
		dlg.showAndWaitForDecision();
	}

}
