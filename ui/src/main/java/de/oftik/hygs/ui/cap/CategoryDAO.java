package de.oftik.hygs.ui.cap;

import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;

public class CategoryDAO extends AbstractDao<Category> {
	public CategoryDAO(ApplicationContext context) {
		super(context, CategoryTable.TABLE);
	}

	@Override
	protected Category instantiate() {
		return new Category();
	}

}
