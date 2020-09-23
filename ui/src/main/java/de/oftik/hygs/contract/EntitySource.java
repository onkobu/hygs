package de.oftik.hygs.contract;

import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Table;

public interface EntitySource<T extends Identifiable<T>> {
	String name();

	Table<T> getTable();

	default Column<T> getPrimaryKeyColumn() {
		return getTable().getPkColumn();
	}

	Column<T> getDeleteColumn();
}
