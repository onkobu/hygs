package de.oftik.hygs.query;

import java.sql.SQLException;
import java.util.function.Consumer;

import de.oftik.hygs.Validators;
import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.DatabaseContext;
import de.oftik.keyhs.kersantti.query.DAO;

public abstract class AbstractDao<T extends Identifiable> extends DAO<T> {

	private final Column<T> deletedColumn;

	public AbstractDao(DatabaseContext context, EntitySource<T> source) {
		super(context, source.getTable());
		this.deletedColumn = source.getDeleteColumn();
	}

	@Override
	public boolean isDatabaseAvailable() {
		return Validators.isValidPath(context().dbPath());
	}

	public void consumeDeleted(Consumer<T> cons) throws SQLException {
		findBy(cons, deletedColumn, true);
	}
}
