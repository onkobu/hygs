package de.oftik.hygs.ui;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class HygsUI {
	private static final Logger log = Logger.getLogger(HygsUI.class.getName());

	public static void main(String[] args) throws Exception {
		final Logger parent = log.getParent();
		parent.addHandler(new ConsoleHandler());
		parent.setLevel(Level.INFO);
		log.info("Starting HygsUI");

		Class.forName("org.sqlite.JDBC");
		final ApplicationContext ctx = new ApplicationContextImpl(args[0]);
		SwingUtilities.invokeLater(() -> {
			HygsFrame frame = new HygsFrame(ctx);
			frame.createUI().setVisible(true);
			frame.pack();
		});
	}

	private static class ApplicationContextImpl implements ApplicationContext {
		private final String dbPath;

		public ApplicationContextImpl(String dbPath) {
			super();
			this.dbPath = dbPath;
		}

		@Override
		public String dbPath() {
			return dbPath;
		}
	}
}
