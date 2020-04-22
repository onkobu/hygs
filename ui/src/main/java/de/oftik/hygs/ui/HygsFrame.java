package de.oftik.hygs.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import de.oftik.hygs.ui.cap.CapabilityPanel;
import de.oftik.hygs.ui.company.CompanyPanel;
import de.oftik.hygs.ui.project.ProjectPanel;
import de.oftik.hygs.ui.trash.TrashPanel;

public class HygsFrame extends JFrame {
	private final ApplicationContext applicationContext;

	public HygsFrame(ApplicationContext ctx) {
		super();
		this.applicationContext = ctx;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public HygsFrame createUI() {
		setJMenuBar(createMenuBar());
		setLayout(new BorderLayout());
		final JTabbedPane centerPane = new JTabbedPane();
		add(centerPane, BorderLayout.CENTER);

		centerPane.add(I18N.COMPANY.title(), new CompanyPanel(applicationContext));
		centerPane.add(I18N.PROJECT.title(), new ProjectPanel(applicationContext));
		centerPane.add(I18N.CAPABILITY.title(), new CapabilityPanel(applicationContext));
		centerPane.add(I18N.EXPORT.title(), new ExportPanel(applicationContext));
		centerPane.add(I18N.TRASH.title(), new TrashPanel(applicationContext));
		return this;
	}

	private JMenuBar createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu databaseMenu = new JMenu(I18N.MENU_DATABASE.title());
		databaseMenu.add(new OpenDatabaseAction(this, applicationContext));
		databaseMenu.add(new CloseDatabaseAction(applicationContext));
		menuBar.add(databaseMenu);
		return menuBar;
	}

	public void startContext() {
		if (applicationContext.hasDatabase()) {
			return;
		}
		new OpenDatabaseAction(this, applicationContext).actionPerformed(null);
	}

	static class OpenDatabaseAction extends AbstractAction {
		private final JFrame owner;
		private final ApplicationContext applicationContext;

		public OpenDatabaseAction(JFrame owner, ApplicationContext applicationContext) {
			super(I18N.ACTION_OPEN_DATABASE.title());
			this.owner = owner;
			this.applicationContext = applicationContext;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser jfc = new JFileChooser(System.getProperty("user.home"));
			final int result = jfc.showOpenDialog(owner);
			if (result != JFileChooser.APPROVE_OPTION) {
				return;
			}
			applicationContext.reInit(jfc.getSelectedFile());
		}
	}

	static class CloseDatabaseAction extends AbstractAction implements ApplicationContextListener {
		private final ApplicationContext applicationContext;

		public CloseDatabaseAction(ApplicationContext applicationContext) {
			super(I18N.ACTION_CLOSE_DATABASE.title());
			this.applicationContext = applicationContext;
			setEnabled(applicationContext.hasDatabase());
			applicationContext.addListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			applicationContext.destroy();
		}

		@Override
		public void onEvent(ContextEvent e) {
			setEnabled(applicationContext.hasDatabase());
		}
	}
}
