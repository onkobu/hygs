package de.oftik.hygs.contract;

import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Table;

public class EntitySourceFixture<I extends de.oftik.kehys.kersantti.OrMappable, T extends Table<I>>
		implements EntitySource<I, T> {

	private final T table;
	private final Column<I> deleteColumn;

	public EntitySourceFixture(T table, Column<I> deleteColumn) {
		super();
		this.table = table;
		this.deleteColumn = deleteColumn;
	}

	@Override
	public T getTable() {
		return table;
	}

	@Override
	public Column<I> getDeleteColumn() {
		return deleteColumn;
	}
}
