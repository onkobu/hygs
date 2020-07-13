package de.oftik.hygs.query.company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.ColumnType;
import de.oftik.keyhs.kersantti.SqlType;

public enum CompanyColumn implements Column<Company> {
	cmp_id(ColumnType.PK_TYPE) {
		@Override
		public void map(Company t, ResultSet rs) throws SQLException {
			t.setId(asString(rs));
		}

		@Override
		public void map(Company t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getId());
		}
	},

	cmp_name(SqlType.VARCHAR.deriveType(64)) {
		@Override
		public void map(Company t, ResultSet rs) throws SQLException {
			t.setName(asString(rs));
		}

		@Override
		public void map(Company t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getName());
		}
	},

	cmp_street(SqlType.VARCHAR.deriveType(128)) {
		@Override
		public void map(Company t, ResultSet rs) throws SQLException {
			t.setStreet(asString(rs));
		}

		@Override
		public void map(Company t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getStreet());
		}
	},

	cmp_city(SqlType.VARCHAR.deriveType(64)) {
		@Override
		public void map(Company t, ResultSet rs) throws SQLException {
			t.setCity(asString(rs));
		}

		@Override
		public void map(Company t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getCity());
		}
	},

	cmp_zip(SqlType.VARCHAR.deriveType(10)) {
		@Override
		public void map(Company t, ResultSet rs) throws SQLException {
			t.setZip(asString(rs));
		}

		@Override
		public void map(Company t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setString(idx, t.getZip());
		}
	},

	cmp_deleted(SqlType.BOOLEAN.deriveType()) {
		@Override
		public void map(Company t, ResultSet rs) throws SQLException {
			t.setDeleted(Boolean.TRUE.equals(asBoolean(rs)));
		}

		@Override
		public void map(Company t, int idx, PreparedStatement stmt) throws SQLException {
			stmt.setBoolean(idx, t.isDeleted());
		}
	};

	private final ColumnType type;

	private CompanyColumn(ColumnType type) {
		this.type = type;
	}

	@Override
	public ColumnType type() {
		return type;
	}
}
