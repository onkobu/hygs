package de.oftik.hygs.ui.cap;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;

public class CategoryDAO extends DAO<Category> {
	public CategoryDAO(ApplicationContext context) {
		super(context, Table.cap_category);
	}

	@Override
	protected Category map(ResultSet rs) throws SQLException {
		return CategoryColumn.to(rs);
	}

	@Override
	protected Column<?> getPkColumn() {
		return CategoryColumn.cat_id;
	}

	@Override
	protected Column<?> getDeletedColumn() {
		return CategoryColumn.cat_deleted;
	}

}
