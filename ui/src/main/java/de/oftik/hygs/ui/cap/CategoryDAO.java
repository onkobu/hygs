package de.oftik.hygs.ui.cap;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Table;

public class CategoryDAO extends DAO<Category> {
	public CategoryDAO(ApplicationContext context) {
		super(context, Table.cap_category);
	}

	@Override
	protected Category map(ResultSet rs) throws SQLException {
		return CategoryColumn.to(rs, new Category());
	}

}
