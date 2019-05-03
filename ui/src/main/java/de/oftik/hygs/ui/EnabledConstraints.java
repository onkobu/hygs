package de.oftik.hygs.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Collection of constraints to automatically enable or disable JComponents
 * based on a Predicate evaluating another component. Place an instance of
 * {@link ConstraintContext} in your panel or top level component. Go on to
 * create buttons and other controllers with the constraint context instance and
 * matching constraints from the factory methods of this class. Also keep in
 * mind to call {@link ConstraintContext#init()} as soon as the top level
 * component displays meaningful data so constraints can be evaluated for a
 * first time. If you miss this step, all components bound in constraints will
 * be displayed with their initial state.
 * 
 * @author onkobu
 *
 */
public class EnabledConstraints {
	/**
	 * 
	 * @author onkobu
	 *
	 */
	public static class ConstraintContext {
		private final List<Constraint<?>> constraints = new ArrayList<>();

		void add(Constraint<?> constraint) {
			constraints.add(constraint);
		}

		public void init() {
			constraints.forEach(Constraint::init);
		}
	}

	static abstract class Constraint<T> {
		private final JComponent component;
		private final Predicate<T> predicate;
		private final T contestant;

		Constraint(JComponent component, Predicate<T> predicate, T contestant) {
			super();
			this.component = component;
			this.predicate = predicate;
			this.contestant = contestant;
		}

		final void evaluate() {
			component.setEnabled(predicate.test(contestant));
		}

		final void init() {
			evaluate();
		}
	}

	static class ListSizeChanged extends Constraint<JList<?>> implements ListDataListener {

		ListSizeChanged(JList<?> list, Predicate<JList<?>> predicate, JComponent component) {
			super(component, predicate, list);
			list.getModel().addListDataListener(this);
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			evaluate();
		}

		@Override
		public void intervalRemoved(ListDataEvent e) {
			evaluate();
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
			evaluate();
		}
	}

	static class ListSelectionChanged extends Constraint<JList<?>> implements ListSelectionListener {
		public ListSelectionChanged(JList<?> list, Predicate<JList<?>> predicate, JButton button) {
			super(button, predicate, list);
			list.addListSelectionListener(this);
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			evaluate();
		}
	}

	public static JButton enableIfFilled(ConstraintContext ctx, JButton button, JList<?> list) {
		ctx.add(new ListSizeChanged(list, (lst) -> list.getModel().getSize() > 0, button));
		return button;
	}

	public static JButton enableIfSelected(ConstraintContext ctx, JButton button, JList<?> list) {
		ctx.add(new ListSelectionChanged(list, (lst) -> lst.getSelectedIndex() != -1, button));
		return button;
	}
}
