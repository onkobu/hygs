package de.oftik.hygs.ui;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

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
		final boolean changed = ((FilterNode) getRoot()).visitAll(term);
		if (changed) {
			fireTreeStructureChanged(this, null, null, null);
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
