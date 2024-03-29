package de.oftik.hygs.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * A node that could be hidden by filtering for properties.
 *
 * @author onkobu
 *
 */
public class FilterNode extends DefaultMutableTreeNode {
	private boolean visible;

	public FilterNode() {
		this(null);
	}

	public FilterNode(IdentifiableBinding<?> userObject) {
		this(userObject, true, true);
	}

	public FilterNode(Object userObject, boolean allowsChildren, boolean isVisible) {
		super(userObject, allowsChildren);
		this.visible = isVisible;
	}

	/**
	 * Replaces the given binding for an existing node or adds the supplied one if
	 * not already present.
	 *
	 * @param childObj      Plain object that could already be displayed (with
	 *                      different data).
	 * @param childSupplier Supplier for a complete child node if it wasn't already
	 *                      present.
	 */
	public void addOrReplace(IdentifiableBinding<?> childObj, Supplier<FilterNode> childSupplier) {
		if (children == null) {
			insert(childSupplier.get(), 0);
			return;
		}
		for (TreeNode tn : children) {
			FilterNode cn = (FilterNode) tn;
			if (((IdentifiableBinding<?>) cn.getUserObject()).getIdentifiable().getId()
					.equals(childObj.getIdentifiable().getId())) {
				cn.setUserObject(childObj);
				return;
			}
		}
		add(childSupplier.get());
	}

	public IdentifiableBinding<?> getBinding() {
		return (IdentifiableBinding<?>) getUserObject();
	}

	public TreeNode getChildAt(int index, boolean filterIsActive) {
		if (!filterIsActive) {
			return super.getChildAt(index);
		}
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}

		int realIndex = -1;
		int visibleIndex = -1;
		Enumeration<TreeNode> e = children.elements();
		while (e.hasMoreElements()) {
			FilterNode node = (FilterNode) e.nextElement();
			if (node.isVisible()) {
				visibleIndex++;
			}
			realIndex++;
			if (visibleIndex == index) {
				return children.elementAt(realIndex);
			}
		}

		throw new ArrayIndexOutOfBoundsException("index unmatched");
	}

	public int getChildCount(boolean filterIsActive) {
		if (!filterIsActive) {
			return super.getChildCount();
		}
		if (children == null) {
			return 0;
		}

		int count = 0;
		Enumeration<TreeNode> e = children.elements();
		while (e.hasMoreElements()) {
			FilterNode node = (FilterNode) e.nextElement();
			if (node.isVisible()) {
				count++;
			}
		}

		return count;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isEmpty() {
		return getChildCount(true) == 0;
	}

	public boolean visitAll(String term) {
		if (isLeaf()) {
			final IdentifiableBinding<?> mts = (IdentifiableBinding<?>) getUserObject();
			final boolean oldState = isVisible();
			setVisible(term == null || mts.matchesTerm(term));
			return oldState != isVisible();
		} else {
			final Enumeration<TreeNode> children = children();
			final int oldCount = getChildCount(true);
			List<FilterNode> changedChildren = new ArrayList<>();
			while (children.hasMoreElements()) {
				final FilterNode child = (FilterNode) children.nextElement();
				if (child.visitAll(term)) {
					changedChildren.add(child);
				}
			}
			setVisible(!isEmpty());
			return oldCount != changedChildren.size();
		}
	}

}
