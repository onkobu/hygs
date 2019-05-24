package de.oftik.hygs.ui;

import java.io.File;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.keyhs.kersantti.DatabaseContext;

public interface ApplicationContext extends DatabaseContext {
	@Override
	String dbPath();

	CommandBroker getBroker();

	boolean hasDatabase();

	void reInit(File selectedFile);

	void addListener(ApplicationContextListener lstnr);

	void removeListener(ApplicationContextListener lstnr);
}
