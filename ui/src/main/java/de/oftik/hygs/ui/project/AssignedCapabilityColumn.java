package de.oftik.hygs.ui.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.orm.Column;

public enum AssignedCapabilityColumn implements Column<AssignedCapability> {
	prj_id {
		@Override
		public void toPojo(ResultSet rs, AssignedCapability t) throws SQLException {
			t.setProjectId(asLong(rs));
		}
	},

	cap_id {
		@Override
		public void toPojo(ResultSet rs, AssignedCapability t) throws SQLException {
			t.setCapabilityId(asLong(rs));
		}
	},

	assigned_weight {
		@Override
		public void toPojo(ResultSet rs, AssignedCapability t) throws SQLException {
			t.setWeight(asInt(rs));
		}
	},

	cap_name {
		@Override
		public void toPojo(ResultSet rs, AssignedCapability t) throws SQLException {
			t.setName(asString(rs));
		}
	},

	cap_version {
		@Override
		public void toPojo(ResultSet rs, AssignedCapability t) throws SQLException {
			t.setVersion(asString(rs));
		}
	};

	public static AssignedCapability to(ResultSet rs, AssignedCapability cap) throws SQLException {
		for (AssignedCapabilityColumn col : values()) {
			col.toPojo(rs, cap);
		}
		return cap;
	}
}
