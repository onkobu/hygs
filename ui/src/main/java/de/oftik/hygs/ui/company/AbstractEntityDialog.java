package de.oftik.hygs.ui.company;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.oftik.hygs.ui.ComponentFactory;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.I18N;

public class AbstractEntityDialog<T> extends JDialog {
	public enum Decision {
		OK, CANCEL;
	}

	public interface OkCallback {
		void okPressed();
	}

	private Decision decision;
	private final EntityForm<T> form;
	private final OkCallback callback;

	public AbstractEntityDialog(JComponent parent, EntityForm<T> main, OkCallback callback) {
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

	public Decision showAndWaitForDecision() {
		decision = null;
		pack();
		setVisible(true);
		if (decision == Decision.OK) {
			callback.okPressed();
		}
		return decision;
	}
}
