package de.oftik.hygs.query.cap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum CapabilityColumn implements Column<Capability> {
	cap_id(ColumnType.PK_TYPE) {
		@Override
		public void map(Capability t, ResultSet rs) throws SQLException {
			t.setId(asString(rs));
		}

		@Override
		public void map(Capability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getId());
		}
	},

	cap_name(SqlType.VARCHAR.deriveType(64)) {

		@Override
		public void map(Capability t, ResultSet rs) throws SQLException {
			t.setName(asString(rs));
		}

		@Override
		public void map(Capability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getName());
		}
	},

	cap_category(SqlType.INTEGER.deriveType()) {
		@Override
		public void map(Capability t, ResultSet rs) throws SQLException {
			t.setCategoryId(asString(rs));
		}

		@Override
		public void map(Capability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getCategoryId());
		}
	},

	cap_version(SqlType.VARCHAR.deriveType(32)) {

		@Override
		public void map(Capability t, ResultSet rs) throws SQLException {
			t.setVersion(asString(rs));
		}

		@Override
		public void map(Capability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getVersion());
		}
	},

	cap_deleted(SqlType.BOOLEAN.deriveType()) {
		@Override
		public void map(Capability t, ResultSet rs) throws SQLException {
			t.setDeleted(Boolean.TRUE.equals(asBoolean(rs)));
		}

		@Override
		public void map(Capability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setBoolean(idx, t.isDeleted());
		}
	};

	private final ColumnType type;

	private CapabilityColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}
}
