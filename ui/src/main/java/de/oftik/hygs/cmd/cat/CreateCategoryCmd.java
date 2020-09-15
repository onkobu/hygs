package de.oftik.hygs.cmd.cat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.cap.Category;
import de.oftik.hygs.query.cap.CategoryColumn;
import de.oftik.hygs.ui.cap.CategoryTable;

public class CreateCategoryCmd implements Command {
	private static final Logger LOG = Logger.getLogger(CreateCategoryCmd.class.getName());

	private final String categoryName;

	public CreateCategoryCmd(String catName) {
		categoryName = catName;
	}

	@Override
	public CommandTarget target() {
		return CommandTargetDefinition.category;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO " + CategoryTable.TABLE.name() + " (" + CategoryColumn.cat_name + ") VALUES (?)",
				Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, categoryName);
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> keysCreated) {
		try {
			return new CategoryCreated(de.oftik.hygs.cmd.AbstractCommand
					.loadSingle(conn, CategoryTable.TABLE, keysCreated, new Category()).get());
		} catch (SQLException e) {
			LOG.throwing(getClass().getSimpleName(), "toNotification", e);
		}
		return null;
	}
}
