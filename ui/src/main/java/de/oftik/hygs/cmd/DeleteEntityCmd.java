package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Table;

public class DeleteEntityCmd implements Command {
	private final CommandTarget target;
	private final Table<?> table;
	private final Column<?> primaryKeyColumn;
	private final Identifiable<?, ?> id;

	public DeleteEntityCmd(Identifiable<?, ?> identifiable) {
		super();
		final EntitySource<?, ?> eSrc = identifiable.getSource();
		this.target = CommandTargetDefinition.targetForTable(eSrc.getTable());
		this.table = eSrc.getTable();
		this.primaryKeyColumn = eSrc.getPrimaryKeyColumn();
		this.id = identifiable;
	}

	@Override
	public CommandTarget target() {
		return target;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = delete(conn, table, primaryKeyColumn);
		stmt.setString(1, id.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new EntityDeleted(target, id);
	}
}
