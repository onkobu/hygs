package de.oftik.hygs.query;

import java.sql.SQLException;
import java.util.function.Consumer;

import de.oftik.hygs.Validators;
import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.DatabaseContext;
import de.oftik.kehys.kersantti.Table;
import de.oftik.kehys.kersantti.query.DAO;

public abstract class AbstractDao<I extends de.oftik.kehys.kersantti.Identifiable, T extends Table<I>, B extends Identifiable<I, T>>
		extends DAO<I> {

	private final Column<I> deletedColumn;

	public AbstractDao(DatabaseContext context, EntitySource<I, T> source) {
		super(context, source.getTable()); // Hyg's layer over ORM requires this trick
		this.deletedColumn = source.getDeleteColumn();
	}

	@Override
	public boolean isDatabaseAvailable() {
		return Validators.isValidPath(context().dbPath());
	}

	public void consumeDeleted(Consumer<B> cons) throws SQLException {
		findBy(ci -> cons.accept(bind(ci)), deletedColumn, true);
	}

	abstract protected B bind(I i);
}
