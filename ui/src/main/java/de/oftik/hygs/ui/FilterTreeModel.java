package de.oftik.hygs.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import de.oftik.hygs.contract.MappableToString;

public class FilterTreeModel extends DefaultTreeModel {

	protected boolean filtering;

	public FilterTreeModel(TreeNode root) {
		this(root, false);
	}

	public FilterTreeModel(TreeNode root, boolean asksAllowsChildren) {
		this(root, false, false);
	}

	public FilterTreeModel(TreeNode root, boolean asksAllowsChildren, boolean filterIsActive) {
		super(root, asksAllowsChildren);
		this.filtering = filterIsActive;
	}

	public void filter(String term) {
		final Enumeration<TreeNode> groups = ((FilterNode) getRoot()).children();
		final String lcTerm;
		if (term == null) {
			lcTerm = null;
		} else {
			lcTerm = term.toLowerCase();
		}
		boolean changed = false;
		while (groups.hasMoreElements()) {
			changed |= visitAll((FilterNode) groups.nextElement(), lcTerm);
		}
		if (changed) {
			fireTreeStructureChanged(this, null, null, null);
		}
	}

	private boolean visitAll(FilterNode node, String term) {
		if (node.isLeaf()) {
			final MappableToString mts = (MappableToString) node.getUserObject();
			final boolean oldState = node.isVisible();
			node.setVisible(term == null || mts.toShortString().toLowerCase().contains(term));
			return oldState != node.isVisible();
		} else {
			final Enumeration<TreeNode> children = node.children();
			List<FilterNode> changedChildren = new ArrayList<>();
			while (children.hasMoreElements()) {
				final FilterNode child = (FilterNode) children.nextElement();
				if (visitAll(child, term)) {
					changedChildren.add(child);
				}
			}
			node.setVisible(!node.isEmpty());
			return false;
		}
	}

	public void setFiltering(boolean newValue) {
		if (filtering == newValue) {
			return;
		}
		filtering = newValue;
		if (filtering == false) {
			filter(null);
		}
	}

	public boolean isFiltering() {
		return filtering;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (filtering) {
			if (parent instanceof FilterNode) {
				return ((FilterNode) parent).getChildAt(index, filtering);
			}
		}
		return ((TreeNode) parent).getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		if (filtering) {
			if (parent instanceof FilterNode) {
				return ((FilterNode) parent).getChildCount(filtering);
			}
		}
		return ((TreeNode) parent).getChildCount();
	}

}
