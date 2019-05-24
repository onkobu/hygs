package de.oftik.hygs.query.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.keyhs.kersantti.Column;

public class CompanyDAO extends AbstractDao<Company> {

	public CompanyDAO(ApplicationContext context) {
		super(context, Table.prj_company);
	}

	@Override
	protected Company map(ResultSet rs) throws SQLException {
		return CompanyColumn.to(rs);
	}

	@Override
	protected Column<?> getPkColumn() {
		return CompanyColumn.cmp_id;
	}

	@Override
	protected Column<?> getDeletedColumn() {
		return CompanyColumn.cmp_deleted;
	}
}
