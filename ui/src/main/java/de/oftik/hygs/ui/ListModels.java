package de.oftik.hygs.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListModels {
	public static <T> void transferAll(DefaultListModel<T> left, DefaultListModel<T> right) {
		for (int i = 0; i < left.size(); i++) {
			right.addElement(left.get(i));
		}
		left.clear();
	}

	public static <T> void transferSelected(JList<T> left, JList<T> right) {
		final int[] idxSel = left.getSelectedIndices();
		// Turn this comment into a special treatment if and only
		// if transferring only a single element becomes a bottleneck
		final List<T> transferred = new ArrayList<>();
		final DefaultListModel<T> targetModel = (DefaultListModel<T>) right.getModel();
		final DefaultListModel<T> sourceModel = (DefaultListModel<T>) left.getModel();
		for (int i = 0; i < idxSel.length; i++) {
			final T elmt = sourceModel.getElementAt(idxSel[i]);
			transferred.add(elmt);
			targetModel.addElement(elmt);
		}
		transferred.forEach(sourceModel::removeElement);
	}
}
