package de.oftik.hygs.query;

import de.oftik.hygs.Validators;
import de.oftik.keyhs.kersantti.DatabaseContext;
import de.oftik.keyhs.kersantti.Table;
import de.oftik.keyhs.kersantti.query.DAO;

public abstract class AbstractDao<T> extends DAO<T> {

	public AbstractDao(DatabaseContext context, Table table) {
		super(context, table);
	}

	@Override
	public boolean isDatabaseAvailable() {
		return Validators.isValidPath(context().dbPath());
	}

}
