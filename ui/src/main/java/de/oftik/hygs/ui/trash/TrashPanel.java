package de.oftik.hygs.ui.trash;

import static de.oftik.hygs.ui.ComponentFactory.createButton;
import static de.oftik.hygs.ui.EnabledConstraints.enableIfFilled;
import static de.oftik.hygs.ui.EnabledConstraints.enableIfSelected;
import static de.oftik.hygs.ui.ListModels.transferAll;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ApplicationContextListener;
import de.oftik.hygs.ui.ComponentFactory;
import de.oftik.hygs.ui.ContextEvent;
import de.oftik.hygs.ui.EnabledConstraints.ConstraintContext;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.ListModels;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class TrashPanel extends JPanel implements ApplicationContextListener {
	private final ApplicationContext applicationContext;
	private final DefaultListModel<Identifiable> trashListModel = new DefaultListModel<>();
	private final DefaultListModel<Identifiable> toDeleteListModel = new DefaultListModel<>();
	private final JList<Identifiable> trash;
	private final JList<Identifiable> toDelete;
	private final CompanyDAO companyDao;
	private final ConstraintContext cCtx = new ConstraintContext();

	static class MappableToStringRenderer implements ListCellRenderer<Identifiable> {
		private final DefaultListCellRenderer delegate = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends Identifiable> list, Identifiable value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (value instanceof MappableToString) {
				return delegate.getListCellRendererComponent(list, ((MappableToString) value).toShortString(), index,
						isSelected, cellHasFocus);
			}
			return delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}

	public TrashPanel(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.companyDao = new CompanyDAO(applicationContext);
		trash = new JList<>(trashListModel);
		trash.setCellRenderer(new MappableToStringRenderer());
		toDelete = new JList<>(toDeleteListModel);
		toDelete.setCellRenderer(new MappableToStringRenderer());
		createUI();
		fillList();
		applicationContext.addListener(this);
	}

	private void createUI() {
		setLayout(new GridBagLayout());
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		add(ComponentFactory.description(I18N.TRASH), gbc.remainderX().weighty(0.1).fillBoth().end());
		add(ComponentFactory.label(I18N.TRASH), gbc.nextRow().end());
		add(ComponentFactory.label(I18N.DELETE), gbc.nextColumn().nextColumn().end());

		add(new JScrollPane(trash), gbc.nextRow().fillBoth().weightx(0.5).weighty(1.0).remainderY().end());
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.ALL_TO_RIGHT, this::allToRight), trash));
		buttonPanel.add(enableIfSelected(cCtx, createButton(I18N.TO_RIGHT, this::toRight), trash));
		buttonPanel.add(enableIfSelected(cCtx, createButton(I18N.TO_LEFT, this::toLeft), toDelete));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.ALL_TO_LEFT, this::allToLeft), toDelete));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.OK, this::makeItSo), toDelete));
		add(buttonPanel, gbc.nextColumn().end());
		add(new JScrollPane(toDelete), gbc.nextColumn().remainderY().fillBoth().weightx(0.5).weighty(1.0).end());
	}

	private final void fillList() {
		try {
			companyDao.consumeDeleted(trashListModel::addElement);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		cCtx.init();
	}

	public void allToRight(ActionEvent evt) {
		transferAll(trashListModel, toDeleteListModel);
	}

	public void allToLeft(ActionEvent evt) {
		transferAll(toDeleteListModel, trashListModel);
	}

	public void toRight(ActionEvent evt) {
		ListModels.transferSelected(trash, toDelete);
	}

	public void toLeft(ActionEvent evt) {
		ListModels.transferSelected(toDelete, trash);
	}

	public void makeItSo(ActionEvent evt) {
	}

	@Override
	public void onEvent(ContextEvent e) {
		switch (e.getEventType()) {
		case RELOAD_DATABASE:
			fillList();
			break;
		}
	}
}
