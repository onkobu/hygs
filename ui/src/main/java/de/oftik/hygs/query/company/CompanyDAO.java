package de.oftik.hygs.query.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;

public class CompanyDAO extends DAO<Company> {

	public CompanyDAO(ApplicationContext context) {
		super(context, Table.prj_company);
	}

	@Override
	protected Company map(ResultSet rs) throws SQLException {
		return CompanyColumn.to(rs);
	}
}
