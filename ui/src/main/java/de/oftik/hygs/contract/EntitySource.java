package de.oftik.hygs.contract;

import de.oftik.hygs.query.Table;
import de.oftik.keyhs.kersantti.Column;

public interface EntitySource {
	String name();

	Table getTable();

	Column<?> getPrimaryKeyColumn();

	Column<?> getDeleteColumn();
}
