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
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.query.cap.CapabilityDAO;
import de.oftik.hygs.query.cap.CategoryDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.FilterNode;
import de.oftik.hygs.ui.GroupedEntityCreateDialog;
import de.oftik.hygs.ui.GroupedEntityPanel;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.IdentifiableBinding;

public class CapabilityPanel extends GroupedEntityPanel<Category, Capability, CapabilityForm, CapabilityDAO> {
	static class CapabilityTreeCellRenderer implements TreeCellRenderer {
		private final DefaultTreeCellRenderer rendererDelegate = new DefaultTreeCellRenderer();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getUserObject() instanceof CategoryBinding) {
				final Category cat = ((CategoryBinding) node.getUserObject()).getIdentifiable();
				return rendererDelegate.getTreeCellRendererComponent(tree, cat.getName(), selected, expanded, false,
						row, hasFocus);
			}

			if (node.getUserObject() instanceof CapabilityBinding) {
				final Capability cap = ((CapabilityBinding) node.getUserObject()).getIdentifiable();
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
	protected boolean isEntityNode(FilterNode node) {
		return node.getUserObject() != null && node.getUserObject() instanceof CapabilityBinding;
	}

	@Override
	protected boolean isGroupNode(FilterNode node) {
		return node.getUserObject() != null && node.getUserObject() instanceof CategoryBinding;
	}

	@Override
	protected List<Capability> loadForGroup(Category g) throws SQLException {
		return entityDao().findByCategory(g);
	}

	@Override
	public void createEntity(ActionEvent evt) {
		final GroupedEntityCreateDialog<Category, Capability, CapabilityForm> dlg = wrapFormAsCreateDialog();
		dlg.getForm().setGroups(extractGroups());
		dlg.showAndWaitForDecision();
	}

	@Override
	public IdentifiableBinding<Capability> bind(Capability e) {
		return new CapabilityBinding(e);
	}

	@Override
	public IdentifiableBinding<Category> bindGroup(Category g) {
		return new CategoryBinding(g);
	}

	static class CapabilityBinding extends IdentifiableBinding<Capability> {
		CapabilityBinding(Capability c) {
			super(c);
		}

		@Override
		public boolean matchesTerm(String term) {
			return getIdentifiable().getName().toLowerCase().contains(term.toLowerCase());
		}
	}

	static class CategoryBinding extends IdentifiableBinding<Category> {

		public CategoryBinding(Category identifiable) {
			super(identifiable);
		}

		@Override
		public boolean matchesTerm(String term) {
			return getIdentifiable().getName().toLowerCase().contains(term.toLowerCase());
		}
	}
}
