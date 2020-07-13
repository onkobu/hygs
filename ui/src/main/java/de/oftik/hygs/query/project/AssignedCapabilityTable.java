package de.oftik.hygs.query.project;

import java.util.Collection;
import java.util.Collections;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Constraint;
import de.oftik.keyhs.kersantti.Table;

public class AssignedCapabilityTable implements Table<AssignedCapability> {
	public static final AssignedCapabilityTable TABLE = new AssignedCapabilityTable();

	private AssignedCapabilityTable() {
	}

	public String name() {
		return "v_prj_cap";
	}

	@Override
	public Column<AssignedCapability> getPkColumn() {
		return AssignedCapabilityColumn.id;
	}

	@Override
	public Column<AssignedCapability>[] columns() {
		return AssignedCapabilityColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}
}
