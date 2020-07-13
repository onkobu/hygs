package de.oftik.hygs.query.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum ProjectColumn implements Column<Project> {
	prj_name(SqlType.VARCHAR.deriveType(64)) {

		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setName(asString(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getName());
		}
	},

	prj_from(SqlType.TIMESTAMP.deriveType()) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setFrom(asLocalDate(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setTimestamp(idx, timestampFrom(t.getFrom()));
		}
	},

	prj_to(SqlType.TIMESTAMP.deriveType()) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setTo(asLocalDate(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setTimestamp(idx, timestampFrom(t.getTo()));
		}
	},

	prj_company(ColumnType.FK_TYPE) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setCompany(asForeignKey(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getCompany().getParentId());
		}
	},

	prj_id(ColumnType.PK_TYPE) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setId(asString(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getId());
		}
	},

	prj_description(SqlType.VARCHAR.deriveType(255)) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setDescription(asString(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getDescription());
		}
	},

	prj_weight(SqlType.INTEGER.deriveType()) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setWeight(asInt(rs));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setInt(idx, t.getWeight());
		}
	},

	prj_deleted(SqlType.BOOLEAN.deriveType()) {
		@Override
		public void map(Project t, ResultSet rs) throws SQLException {
			t.setDeleted(Boolean.TRUE.equals(asBoolean(rs)));
		}

		@Override
		public void map(Project t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setBoolean(idx, t.isDeleted());
		}
	};

	private final ColumnType type;

	private ProjectColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}

}
