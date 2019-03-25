package de.oftik.hygs;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.HygsFrame;

public class HygsUI {
	private static final Logger log = Logger.getLogger(HygsUI.class.getName());

	public static void main(String[] args) throws Exception {
		try {
			LogManager.getLogManager().readConfiguration(HygsUI.class.getResourceAsStream("/logging.properties"));
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}

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
