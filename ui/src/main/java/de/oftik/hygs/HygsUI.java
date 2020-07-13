package de.oftik.hygs;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ApplicationContextListener;
import de.oftik.hygs.ui.ContextEvent;
import de.oftik.hygs.ui.HygsFrame;
import de.oftik.hygs.ui.Listenable;

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
		final ApplicationContextImpl ctx;
		if (args == null || args.length == 0) {
			ctx = new ApplicationContextImpl();
		} else {
			ctx = new ApplicationContextImpl(args[0]);
		}

		final CommandBroker cmdBroker = new CommandBroker();
		Arrays.stream(CommandTargetDefinition.values()).map((dfn) -> dfn.queue(ctx)).forEach(cmdBroker::registerQueue);

		ctx.registerBroker(cmdBroker);

		SwingUtilities.invokeLater(() -> {
			HygsFrame frame = new HygsFrame(ctx);
			frame.createUI().setVisible(true);
			frame.pack();
			frame.startContext();
		});
	}

	private static class ApplicationContextImpl extends Listenable<ContextEvent, ApplicationContextListener>
			implements ApplicationContext {
		private String dbPath;
		private CommandBroker commandBroker;

		public ApplicationContextImpl() {

		}

		public ApplicationContextImpl(String dbPath) {
			super();
			this.dbPath = dbPath;
		}

		@Override
		public String dbPath() {
			return dbPath;
		}

		@Override
		public void reInit(File selectedFile) {
			dbPath = selectedFile.getAbsolutePath();
			publish(ContextEvent.reloadDatabase());
		}

		@Override
		public void destroy() {
			dbPath = null;
			publish(ContextEvent.closedDatabase());
		}

		@Override
		public boolean hasDatabase() {
			return Validators.isValidPath(dbPath());
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

		@Override
		public Connection getOrCreateConnection() throws SQLException {
			String url = "jdbc:sqlite:" + dbPath();
			return DriverManager.getConnection(url);
		}

		@Override
		public boolean isDatabaseAvailable() {
			return true;
		}
	}
}
