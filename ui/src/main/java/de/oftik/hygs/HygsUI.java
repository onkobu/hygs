package de.oftik.hygs;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.cat.CategoryQueue;
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
		final ApplicationContextImpl ctx = new ApplicationContextImpl(args[0]);

		final CommandBroker cmdBroker = new CommandBroker();
		cmdBroker.registerQueue(new CategoryQueue(ctx));

		ctx.registerBroker(cmdBroker);

		SwingUtilities.invokeLater(() -> {
			HygsFrame frame = new HygsFrame(ctx);
			frame.createUI().setVisible(true);
			frame.pack();
		});
	}

	private static class ApplicationContextImpl implements ApplicationContext {
		private final String dbPath;
		private CommandBroker commandBroker;

		public ApplicationContextImpl(String dbPath) {
			super();
			this.dbPath = dbPath;
		}

		@Override
		public String dbPath() {
			return dbPath;
		}

		void registerBroker(CommandBroker cmdBroker) {
			if (commandBroker != null) {
				throw new IllegalStateException("Cannot register another command broker");
			}
			commandBroker = cmdBroker;
		}

		@Override
		public CommandBroker getBroker() {
			return commandBroker;
		}
	}
}
