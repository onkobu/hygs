package de.oftik.hygs.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import de.oftik.hygs.ui.cap.CapabilityPanel;
import de.oftik.hygs.ui.company.CompanyPanel;
import de.oftik.hygs.ui.project.ProjectPanel;

public class HygsFrame extends JFrame {
	private final ApplicationContext applicationContext;

	public HygsFrame(ApplicationContext ctx) {
		super();
		this.applicationContext = ctx;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	HygsFrame createUI() {
		setLayout(new BorderLayout());
		final JTabbedPane centerPane = new JTabbedPane();
		add(centerPane, BorderLayout.CENTER);

		centerPane.add(I18N.COMPANY.title(), new CompanyPanel(applicationContext));
		centerPane.add(I18N.PROJECT.title(), new ProjectPanel(applicationContext));
		centerPane.add(I18N.CAPABILITY.title(), new CapabilityPanel(applicationContext));
		centerPane.add(I18N.EXPORT.title(), new ExportPanel(applicationContext));
		return this;
	}
}
