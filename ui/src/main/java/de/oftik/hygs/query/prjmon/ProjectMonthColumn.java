package de.oftik.hygs.query.prjmon;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;

public enum ProjectMonthColumn implements Column<ProjectMonth> {
	prj_id,

	prj_name,

	prj_months;

	public static ProjectMonth to(ResultSet rs) throws SQLException {
		return new ProjectMonth(prj_id.asLong(rs), prj_name.asString(rs), prj_months.asInt(rs));
	}
}
