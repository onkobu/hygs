package de.oftik.hygs.query.company;

import java.util.Collection;
import java.util.Collections;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.kehys.kersantti.Column;
import de.oftik.kehys.kersantti.Constraint;
import de.oftik.kehys.kersantti.Table;

public final class CompanyTable implements Table<Company>, EntitySource<Company> {
	public static final CompanyTable TABLE = new CompanyTable();

	private CompanyTable() {
	}

	@Override
	public String name() {
		return "prj_company";
	}

	@Override
	public Column<Company> getPkColumn() {
		return CompanyColumn.cmp_id;
	}

	@Override
	public Column<Company>[] columns() {
		return CompanyColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}

	@Override
	public Table<Company> getTable() {
		return this;
	}

	@Override
	public Column<Company> getDeleteColumn() {
		return CompanyColumn.cmp_deleted;
	}

}
