package de.oftik.hygs.contract;

import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.OrMappable;
import de.oftik.kehys.kersantti.Table;

public interface EntitySource<I extends OrMappable, T extends Table<I>> {
	default String name() {
		return getTable().name();
	}

	T getTable();

	default Column<I> getPrimaryKeyColumn() {
		return getTable().getPkColumn();
	}

	Column<I> getDeleteColumn();
}
