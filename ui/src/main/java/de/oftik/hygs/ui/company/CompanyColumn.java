package de.oftik.hygs.ui.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.orm.Column;

public enum CompanyColumn implements Column<Company> {
	cmp_id {
		@Override
		public void toPojo(ResultSet rs, Company cmp) throws SQLException {
			cmp.setId(asLong(rs));
		}
	},

	cmp_name {
		@Override
		public void toPojo(ResultSet rs, Company cmp) throws SQLException {
			cmp.setName(asString(rs));
		}
	},

	cmp_street {
		@Override
		public void toPojo(ResultSet rs, Company cmp) throws SQLException {
			cmp.setStreet(asString(rs));
		}
	},

	cmp_city {
		@Override
		public void toPojo(ResultSet rs, Company cmp) throws SQLException {
			cmp.setCity(asString(rs));
		}
	},

	cmp_zip {
		@Override
		public void toPojo(ResultSet rs, Company cmp) throws SQLException {
			cmp.setZip(asString(rs));
		}
	};

	public static Company to(ResultSet rs, Company company) throws SQLException {
		for (CompanyColumn col : values()) {
			col.toPojo(rs, company);
		}
		return company;
	}

}
