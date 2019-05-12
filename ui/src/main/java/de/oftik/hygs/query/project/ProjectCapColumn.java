package de.oftik.hygs.query.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;

public enum ProjectCapColumn implements Column<ProjectCapMapping> {
	prj_id,

	cap_id,

	assigned_weight;

	public static ProjectCapMapping to(ResultSet rs) throws SQLException {
		return new ProjectCapMapping(prj_id.asLong(rs), cap_id.asLong(rs), assigned_weight.asInt(rs));
	}
}
