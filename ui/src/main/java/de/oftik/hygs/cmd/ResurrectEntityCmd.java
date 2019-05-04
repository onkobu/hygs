package de.oftik.hygs.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.Table;

public class ResurrectEntityCmd implements Command {
	private final CommandTarget target;
	private final Table table;
	private final Column<?> primaryKeyColumn;
	private final Column<?> deleteColumn;
	private final long id;

	public ResurrectEntityCmd(Identifiable identifiable) {
		super();
		final EntitySource eSrc = identifiable.getSource();
		this.target = CommandTargetDefinition.targetForTable(eSrc.getTable());
		this.table = eSrc.getTable();
		this.primaryKeyColumn = eSrc.getPrimaryKeyColumn();
		this.deleteColumn = eSrc.getDeleteColumn();
		this.id = identifiable.getId();
	}

	@Override
	public CommandTarget target() {
		return target;
	}

	@Override
	public PreparedStatement prepare(Connection conn) throws SQLException {
		final PreparedStatement stmt = resurrect(conn, table, primaryKeyColumn, deleteColumn);
		stmt.setLong(1, id);
		return stmt;
	}

	@Override
	public Notification toNotification(List<Long> generatedKeys) {
		return new EntityResurrected(target, generatedKeys.get(0));
	}
}
