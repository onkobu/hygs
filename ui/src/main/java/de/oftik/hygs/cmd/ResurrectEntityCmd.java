package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Table;

public class ResurrectEntityCmd implements Command {
	private final CommandTarget target;
	private final Table<?> table;
	private final Column<?> primaryKeyColumn;
	private final Column<?> deleteColumn;
	private final Identifiable<?, ?> id;

	public ResurrectEntityCmd(Identifiable<?, ?> identifiable) {
		super();
		final EntitySource<?, ?> eSrc = identifiable.getSource();
		this.target = CommandTargetDefinition.targetForTable(eSrc.getTable());
		this.table = eSrc.getTable();
		this.primaryKeyColumn = eSrc.getPrimaryKeyColumn();
		this.deleteColumn = eSrc.getDeleteColumn();
		this.id = identifiable;
	}

	@Override
	public CommandTarget target() {
		return target;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = resurrect(conn, table, primaryKeyColumn, deleteColumn);
		stmt.setString(1, id.getId());
		return stmt;
	}

	@Override
	public Notification toNotification(Connection conn, List<String> generatedKeys) {
		return new EntityResurrected(target, id);
	}
}
