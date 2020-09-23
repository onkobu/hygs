package de.oftik.hygs.query.prjmon;

import java.util.Collection;
import java.util.Collections;

import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Constraint;
import de.oftik.kehys.kersantti.Table;

public class ProjectMonthTable implements Table<ProjectMonth> {
	public static final ProjectMonthTable TABLE = new ProjectMonthTable();

	private ProjectMonthTable() {
	}

	public String name() {
		return "prj_months";
	}

	@Override
	public Column<ProjectMonth> getPkColumn() {
		return ProjectMonthColumn.prj_id;
	}

	@Override
	public Column<ProjectMonth>[] columns() {
		return ProjectMonthColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}
}
