package de.oftik.hygs.ui.trash;

import static de.oftik.hygs.ui.ComponentFactory.createButton;
import static de.oftik.hygs.ui.EnabledConstraints.enableIfFilled;
import static de.oftik.hygs.ui.EnabledConstraints.enableIfSelected;
import static de.oftik.hygs.ui.ListModels.transferAll;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.oftik.hygs.cmd.ResurrectEntityCmd;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.query.project.ProjectDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ApplicationContextListener;
import de.oftik.hygs.ui.ComponentFactory;
import de.oftik.hygs.ui.ContextEvent;
import de.oftik.hygs.ui.EnabledConstraints.ConstraintContext;
import de.oftik.hygs.ui.I18N;
import de.oftik.hygs.ui.ListModels;
import de.oftik.hygs.ui.MappableToStringRenderer;
import de.oftik.hygs.ui.cap.CategoryDAO;
import de.oftik.kehys.keijukainen.gui.GridBagConstraintFactory;

public class TrashPanel extends JPanel implements ApplicationContextListener {
	private final ApplicationContext applicationContext;
	private final DefaultListModel<Identifiable> trashListModel = new DefaultListModel<>();
	private final DefaultListModel<Identifiable> toProcessListModel = new DefaultListModel<>();
	private final JList<Identifiable> trash;
	private final JList<Identifiable> toProcess;
	private final CompanyDAO companyDao;
	private final ConstraintContext cCtx = new ConstraintContext();
	private final CategoryDAO categoryDao;
	private final ProjectDAO projectDao;

	public TrashPanel(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.categoryDao = new CategoryDAO(applicationContext);
		this.companyDao = new CompanyDAO(applicationContext);
		this.projectDao = new ProjectDAO(applicationContext);
		trash = new JList<>(trashListModel);
		trash.setCellRenderer(new MappableToStringRenderer());
		toProcess = new JList<>(toProcessListModel);
		toProcess.setCellRenderer(new MappableToStringRenderer());
		createUI();
		fillList();
		applicationContext.addListener(this);
	}

	private void createUI() {
		setLayout(new GridBagLayout());
		final GridBagConstraintFactory gbc = GridBagConstraintFactory.gridBagConstraints();
		add(ComponentFactory.description(I18N.TRASH), gbc.remainderX().weighty(0.1).fillBoth().end());
		add(ComponentFactory.label(I18N.TRASH), gbc.nextRow().end());
		add(ComponentFactory.label(I18N.PROCESS), gbc.nextColumn().nextColumn().end());

		add(new JScrollPane(trash), gbc.nextRow().fillBoth().weightx(0.5).weighty(1.0).remainderY().end());
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.ALL_TO_RIGHT, this::allToRight), trash));
		buttonPanel.add(enableIfSelected(cCtx, createButton(I18N.TO_RIGHT, this::toRight), trash));
		buttonPanel.add(enableIfSelected(cCtx, createButton(I18N.TO_LEFT, this::toLeft), toProcess));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.ALL_TO_LEFT, this::allToLeft), toProcess));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.RESURRECT, this::resurrect), toProcess));
		buttonPanel.add(enableIfFilled(cCtx, createButton(I18N.DELETE, this::delete), toProcess));
		add(buttonPanel, gbc.nextColumn().end());
		add(new JScrollPane(toProcess), gbc.nextColumn().remainderY().fillBoth().weightx(0.5).weighty(1.0).end());
	}

	private final void fillList() {
		try {
			categoryDao.consumeDeleted(trashListModel::addElement);
			companyDao.consumeDeleted(trashListModel::addElement);
			projectDao.consumeDeleted(trashListModel::addElement);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		cCtx.init();
	}

	public void allToRight(ActionEvent evt) {
		transferAll(trashListModel, toProcessListModel);
	}

	public void allToLeft(ActionEvent evt) {
		transferAll(toProcessListModel, trashListModel);
	}

	public void toRight(ActionEvent evt) {
		ListModels.transferSelected(trash, toProcess);
	}

	public void toLeft(ActionEvent evt) {
		ListModels.transferSelected(toProcess, trash);
	}

	public void delete(ActionEvent evt) {
	}

	public void resurrect(ActionEvent evt) {
		if (toProcessListModel.size() == 0) {
			return;
		}
		for (int i = 0; i < toProcessListModel.getSize(); i++) {
			applicationContext.getBroker().execute(new ResurrectEntityCmd(toProcessListModel.get(i)));
		}
		toProcessListModel.clear();
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
