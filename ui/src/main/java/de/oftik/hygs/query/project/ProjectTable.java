package de.oftik.hygs.query.project;

import java.util.Collection;
import java.util.Collections;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Constraint;
import de.oftik.keyhs.kersantti.Table;

public final class ProjectTable implements Table<Project>, EntitySource<Project> {
	public static final ProjectTable TABLE = new ProjectTable();

	private ProjectTable() {
	}

	@Override
	public String name() {
		return "cap_project";
	}

	@Override
	public Table<Project> getTable() {
		return this;
	}

	@Override
	public Column<Project> getDeleteColumn() {
		return ProjectColumn.prj_deleted;
	}

	@Override
	public Column<Project> getPkColumn() {
		return ProjectColumn.prj_id;
	}

	@Override
	public Column<Project>[] columns() {
		return ProjectColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}
}
