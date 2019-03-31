package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface Command {

	CommandTarget target();

	PreparedStatement prepare(Connection conn) throws SQLException;

	Notification toNotification(List<Long> generatedKeys);
}
