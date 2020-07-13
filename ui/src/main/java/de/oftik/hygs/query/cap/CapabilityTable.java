package de.oftik.hygs.query.cap;

import java.util.Collection;
import java.util.Collections;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Constraint;
import de.oftik.keyhs.kersantti.Table;

public final class CapabilityTable implements Table<Capability>, EntitySource<Capability> {
	public static final CapabilityTable TABLE = new CapabilityTable();

	private CapabilityTable() {
	}

	@Override
	public String name() {
		return "cap_capability";
	}

	@Override
	public Column<Capability> getPkColumn() {
		return CapabilityColumn.cap_id;
	}

	@Override
	public Column<Capability>[] columns() {
		return CapabilityColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}

	@Override
	public Table<Capability> getTable() {
		return this;
	}

	@Override
	public Column<Capability> getDeleteColumn() {
		return CapabilityColumn.cap_deleted;
	}

}
