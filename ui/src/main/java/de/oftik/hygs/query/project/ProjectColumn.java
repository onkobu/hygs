package de.oftik.hygs.query.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;

public enum ProjectColumn implements Column<Project> {
	prj_name,

	prj_from,

	prj_to,

	prj_company,

	prj_id,

	prj_description,

	prj_weight,

	prj_deleted;

	public static Project to(ResultSet rs) throws SQLException {
		return new Project(prj_id.asLong(rs), prj_name.asString(rs), prj_from.asLocalDate(rs), prj_to.asLocalDate(rs),
				prj_company.asLong(rs), prj_description.asString(rs), prj_weight.asInt(rs));
	}
}
