package de.oftik.hygs.ui.cap;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.orm.Column;

public enum CapabilityColumn implements Column<Capability> {
	cap_id {
		@Override
		public void toPojo(ResultSet rs, Capability t) throws SQLException {
			t.setId(asLong(rs));
		}
	},

	cap_name {
		@Override
		public void toPojo(ResultSet rs, Capability t) throws SQLException {
			t.setName(asString(rs));
		}
	},

	cap_category {
		@Override
		public void toPojo(ResultSet rs, Capability t) throws SQLException {
			t.setCategoryId(asLong(rs));
		}
	},

	cap_version {
		@Override
		public void toPojo(ResultSet rs, Capability t) throws SQLException {
			t.setVersion(asString(rs));
		}
	};

	public static Capability to(ResultSet rs, Capability cap) throws SQLException {
		for (CapabilityColumn col : values()) {
			col.toPojo(rs, cap);
		}
		return cap;
	}
}
