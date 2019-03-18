package de.oftik.hygs.ui;

import javax.swing.SwingUtilities;

public class HygsUI {
	public static void main(String[] args) throws Exception {
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
