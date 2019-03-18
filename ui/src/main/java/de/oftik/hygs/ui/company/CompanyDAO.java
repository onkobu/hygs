package de.oftik.hygs.ui.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.DAO;
import de.oftik.hygs.ui.Table;

public class CompanyDAO extends DAO<Company> {

	public CompanyDAO(ApplicationContext context) {
		super(context, Table.prj_company);
	}

	@Override
	protected Company map(ResultSet rs) throws SQLException {
		return new Company(rs.getLong("cmp_id"), rs.getString("cmp_name"), rs.getString("cmp_street"),
				rs.getString("cmp_city"), rs.getString("cmp_zip"));
	}
}
