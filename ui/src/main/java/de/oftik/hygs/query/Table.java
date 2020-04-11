package de.oftik.hygs.query;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.query.cap.CapabilityColumn;
import de.oftik.hygs.query.company.CompanyColumn;
import de.oftik.hygs.query.project.ProjectColumn;
import de.oftik.hygs.ui.cap.CategoryColumn;
import de.oftik.keyhs.kersantti.Column;

public enum Table implements de.oftik.keyhs.kersantti.Table, EntitySource {
	prj_company(CompanyColumn.cmp_id, CompanyColumn.cmp_deleted),

	prj_months,

	cap_project(ProjectColumn.prj_id, ProjectColumn.prj_deleted),

	cap_category(CategoryColumn.cat_id, CategoryColumn.cat_deleted),

	cap_capability(CapabilityColumn.cap_id, CapabilityColumn.cap_deleted),

	prj_cap_mapping,

	v_prj_cap;

	private final Column<?> pkColumn;
	private final Column<?> delColumn;

	private Table() {
		this(null, null);
	}

	private Table(Column<?> pkColumn, Column<?> delColumn) {
		this.pkColumn = pkColumn;
		this.delColumn = delColumn;
	}

	@Override
	public Table getTable() {
		return this;
	}

	@Override
	public Column<?> getPrimaryKeyColumn() {
		if (pkColumn == null) {
			throw new UnsupportedOperationException();
		}
		return pkColumn;
	}

	@Override
	public Column<?> getDeleteColumn() {
		if (delColumn == null) {
			throw new UnsupportedOperationException();
		}
		return delColumn;
	}

}
