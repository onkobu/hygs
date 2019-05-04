package de.oftik.hygs.contract;

import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.Table;

public interface EntitySource {
	String name();

	Table getTable();

	Column<?> getPrimaryKeyColumn();

	Column<?> getDeleteColumn();
}
