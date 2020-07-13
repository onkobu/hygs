package de.oftik.hygs.query.prjmon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum ProjectMonthColumn implements Column<ProjectMonth> {
	prj_id(ColumnType.PK_TYPE) {
		@Override
		public void map(ProjectMonth t, ResultSet rs) throws SQLException {
			t.setId(asString(rs));
		}

		@Override
		public void map(ProjectMonth t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getId());
		}
	},

	prj_name(SqlType.VARCHAR.deriveType(64)) {
		@Override
		public void map(ProjectMonth t, ResultSet rs) throws SQLException {
			t.setProjectName(asString(rs));
		}

		@Override
		public void map(ProjectMonth t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getProjectName());
		}
	},

	prj_months(SqlType.INTEGER.deriveType()) {
		@Override
		public void map(ProjectMonth t, ResultSet rs) throws SQLException {
			t.setMonths(asInt(rs));
		}

		@Override
		public void map(ProjectMonth t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setInt(idx, t.getMonths());
		}
	};

	private final ColumnType type;

	private ProjectMonthColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}
}
