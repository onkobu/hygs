package de.oftik.hygs.cmd.cat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.oftik.hygs.cmd.Command;
import de.oftik.hygs.cmd.CommandTarget;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.cap.CategoryColumn;

public class CreateCategoryCmd implements Command {
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
				"INSERT INTO " + Table.cap_category + " (" + CategoryColumn.cat_name + ") VALUES (?)",
				Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, categoryName);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> keysCreated) {
		return new CategoryCreated(keysCreated.get(0));
	}
}
