package de.oftik.hygs.ui.cap;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;

public enum CategoryColumn implements Column<Category> {
	cat_id,

	cat_name;

	public static Category to(ResultSet rs) throws SQLException {
		return new Category(cat_id.asLong(rs), cat_name.asString(rs));
	}
}
