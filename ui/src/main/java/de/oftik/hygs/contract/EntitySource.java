package de.oftik.hygs.contract;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Table;

public interface EntitySource<T extends Identifiable> {
	String name();

	Table<T> getTable();

	default Column<T> getPrimaryKeyColumn() {
		return getTable().getPkColumn();
	}

	Column<T> getDeleteColumn();
}
