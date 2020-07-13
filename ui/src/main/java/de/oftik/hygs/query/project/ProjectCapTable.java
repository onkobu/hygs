package de.oftik.hygs.query.project;

import java.util.Collection;
import java.util.Collections;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Constraint;
import de.oftik.keyhs.kersantti.Table;

public final class ProjectCapTable implements Table<ProjectCapMapping> {
	public static final ProjectCapTable TABLE = new ProjectCapTable();

	private ProjectCapTable() {

	}

	@Override
	public String name() {
		return "prj_cap_mapping";
	}

	@Override
	public Column<ProjectCapMapping> getPkColumn() {
		return ProjectCapColumn.cap_id;
	}

	@Override
	public Column<ProjectCapMapping>[] columns() {
		return ProjectCapColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}
}
