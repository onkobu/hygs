package de.oftik.hygs.query.cap;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;

public enum CapabilityColumn implements Column<Capability> {
	cap_id,

	cap_name,

	cap_category,

	cap_version,

	cap_deleted;

	public static Capability to(ResultSet rs) throws SQLException {
		return new Capability(cap_id.asLong(rs), cap_name.asString(rs), cap_category.asLong(rs),
				cap_version.asString(rs));
	}
}
