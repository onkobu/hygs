package de.oftik.hygs.ui.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Table;

public class CompanyDAO extends DAO<Company> {

	public CompanyDAO(ApplicationContext context) {
		super(context, Table.prj_company);
	}

	@Override
	protected Company map(ResultSet rs) throws SQLException {
		return CompanyColumn.to(rs, new Company());
	}
}
