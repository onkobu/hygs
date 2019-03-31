package de.oftik.hygs.ui;

import de.oftik.hygs.cmd.CommandBroker;

public interface ApplicationContext {
	String dbPath();

	CommandBroker getBroker();
}
