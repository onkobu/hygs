package de.oftik.hygs.query.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;

public enum CompanyColumn implements Column<Company> {
	cmp_id,

	cmp_name,

	cmp_street,

	cmp_city,

	cmp_zip,

	cmp_deleted;

	public static Company to(ResultSet rs) throws SQLException {
		return new Company(cmp_id.asLong(rs), cmp_name.asString(rs), cmp_street.asString(rs), cmp_city.asString(rs),
				cmp_zip.asString(rs));
	}

}
