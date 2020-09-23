package de.oftik.hygs.query.project;

import java.util.Collection;
import java.util.Collections;

import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Constraint;
import de.oftik.kehys.kersantti.Table;

public class AssignedCapabilityTable implements Table<AssignedCapability> {
	public static final AssignedCapabilityTable TABLE = new AssignedCapabilityTable();

	private AssignedCapabilityTable() {
	}

	public String name() {
		return "v_prj_cap";
	}

	@Override
	public Column<AssignedCapability> getPkColumn() {
		return null;
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
