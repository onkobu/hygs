package de.oftik.hygs.query.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum ProjectCapColumn implements Column<ProjectCapMapping> {
	prj_id(ColumnType.FK_TYPE) {
		@Override
		public void map(ProjectCapMapping t, ResultSet rs) throws SQLException {
			t.setProject(asForeignKey(rs));
		}

		@Override
		public void map(ProjectCapMapping t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getProject().getParentId());
		}
	},

	cap_id(ColumnType.FK_TYPE) {
		@Override
		public void map(ProjectCapMapping t, ResultSet rs) throws SQLException {
			t.setCapability(asForeignKey(rs));
		}

		@Override
		public void map(ProjectCapMapping t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getCapability().getParentId());
		}
	},

	assigned_weight(SqlType.INTEGER.deriveType()) {
		@Override
		public void map(ProjectCapMapping t, ResultSet rs) throws SQLException {
			t.setAssignedWeight(asInt(rs));
		}

		@Override
		public void map(ProjectCapMapping t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setInt(idx, t.getAssignedWeight());
		}
	};

	private final ColumnType type;

	private ProjectCapColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}

}
