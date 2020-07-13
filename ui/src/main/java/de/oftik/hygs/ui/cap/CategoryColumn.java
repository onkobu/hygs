package de.oftik.hygs.ui.cap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum CategoryColumn implements Column<Category> {
	cat_id(ColumnType.PK_TYPE) {
		@Override
		public void map(Category t, ResultSet rs) throws SQLException {
			t.setId(asString(rs));
		}

		@Override
		public void map(Category t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getId());
		}
	},

	cat_name(SqlType.VARCHAR.deriveType(64)) {
		@Override
		public void map(Category t, ResultSet rs) throws SQLException {
			t.setName(asString(rs));
		}

		@Override
		public void map(Category t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getName());
		}
	},

	cat_deleted(SqlType.BOOLEAN.deriveType()) {
		@Override
		public void map(Category t, ResultSet rs) throws SQLException {
			t.setDeleted(Boolean.TRUE.equals(asBoolean(rs)));
		}

		@Override
		public void map(Category t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setBoolean(idx, t.isDeleted());
		}
	};

	private final ColumnType type;

	private CategoryColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}

}
