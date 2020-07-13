package de.oftik.hygs.query.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum AssignedCapabilityColumn implements Column<AssignedCapability> {
	id(ColumnType.PK_TYPE) {
		@Override
		public void map(AssignedCapability t, ResultSet rs) throws SQLException {
			t.setId(asString(rs));
		}

		@Override
		public void map(AssignedCapability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getId());
		}
	},

	prj_id(ColumnType.FK_TYPE) {
		@Override
		public void map(AssignedCapability t, ResultSet rs) throws SQLException {
			t.setProject(asForeignKey(rs));
		}

		@Override
		public void map(AssignedCapability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getProject().getParentId());
		}
	},

	cap_id(ColumnType.FK_TYPE) {
		@Override
		public void map(AssignedCapability t, ResultSet rs) throws SQLException {
			t.setProject(asForeignKey(rs));
		}

		@Override
		public void map(AssignedCapability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getProject().getParentId());
		}
	},

	assigned_weight(SqlType.INTEGER.deriveType()) {
		@Override
		public void map(AssignedCapability t, ResultSet rs) throws SQLException {
			t.setWeight(asInt(rs));
		}

		@Override
		public void map(AssignedCapability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setInt(idx, t.getWeight());
		}
	},

	cap_name(SqlType.VARCHAR.deriveType(64)) {
		@Override
		public void map(AssignedCapability t, ResultSet rs) throws SQLException {
			t.setName(asString(rs));
		}

		@Override
		public void map(AssignedCapability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getName());
		}
	},

	cap_version(SqlType.VARCHAR.deriveType(32)) {
		@Override
		public void map(AssignedCapability t, ResultSet rs) throws SQLException {
			t.setVersion(asString(rs));
		}

		@Override
		public void map(AssignedCapability t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getVersion());
		}
	};

	private final ColumnType type;

	private AssignedCapabilityColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}

}
