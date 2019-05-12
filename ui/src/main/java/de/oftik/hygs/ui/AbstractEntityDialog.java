package de.oftik.hygs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AbstractEntityDialog<T, F extends EntityForm<T>> extends JDialog {
	public enum Decision {
		OK, CANCEL;
	}

	public interface OkCallback {
		void okPressed();
	}

	private Decision decision;
	private final F form;
	private final OkCallback callback;

	public AbstractEntityDialog(JComponent parent, F main, OkCallback callback) {
		super(SwingUtilities.getWindowAncestor(parent));
		setModal(true);
		setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		buttonPanel.add(ComponentFactory.createButton(I18N.OK, this::okPressed));
		buttonPanel.add(ComponentFactory.createButton(I18N.CANCEL, this::cancelPressed));
		add(buttonPanel, BorderLayout.SOUTH);
		this.form = main;
		this.callback = callback;
	}

	public void okPressed(ActionEvent evt) {
		setVisible(false);
		this.decision = Decision.OK;
	}

	public void cancelPressed(ActionEvent evt) {
		setVisible(false);
		decision = Decision.CANCEL;
	}

	public F getForm() {
		return form;
	}

	public Decision showAndWaitForDecision() {
		decision = null;
		pack();
		setVisible(true);
		if (decision == Decision.OK) {
			callback.okPressed();
		}
		form.destroy();
		return decision;
	}
}
