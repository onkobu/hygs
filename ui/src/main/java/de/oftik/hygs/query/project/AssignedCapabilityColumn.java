package de.oftik.hygs.query.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;

public enum AssignedCapabilityColumn implements Column<AssignedCapability> {
	prj_id,

	cap_id,

	assigned_weight,

	cap_name,

	cap_version;

	public static AssignedCapability to(ResultSet rs) throws SQLException {
		return new AssignedCapability(prj_id.asLong(rs), cap_id.asLong(rs), assigned_weight.asInt(rs),
				cap_name.asString(rs), cap_version.asString(rs));
	}
}
