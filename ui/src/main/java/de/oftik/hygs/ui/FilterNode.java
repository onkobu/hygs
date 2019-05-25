package de.oftik.hygs.ui;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class FilterNode extends DefaultMutableTreeNode {
	private boolean visible;

	public FilterNode() {
		this(null);
	}

	public FilterNode(Object userObject) {
		this(userObject, true, true);
	}

	public FilterNode(Object userObject, boolean allowsChildren, boolean isVisible) {
		super(userObject, allowsChildren);
		this.visible = isVisible;
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
		Enumeration e = children.elements();
		while (e.hasMoreElements()) {
			FilterNode node = (FilterNode) e.nextElement();
			if (node.isVisible()) {
				visibleIndex++;
			}
			realIndex++;
			if (visibleIndex == index) {
				return (TreeNode) children.elementAt(realIndex);
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
		Enumeration e = children.elements();
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

}
