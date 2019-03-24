package de.oftik.hygs.ui.cap;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.orm.Column;

public enum CategoryColumn implements Column<Category> {
	cat_id {
		@Override
		public void toPojo(ResultSet rs, Category t) throws SQLException {
			t.setId(asLong(rs));
		}
	},

	cat_name {
		@Override
		public void toPojo(ResultSet rs, Category t) throws SQLException {
			t.setName(asString(rs));
		}
	};

	public static Category to(ResultSet rs, Category cat) throws SQLException {
		for (CategoryColumn col : values()) {
			col.toPojo(rs, cat);
		}
		return cat;
	}
}
