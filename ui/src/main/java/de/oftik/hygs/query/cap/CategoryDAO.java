package de.oftik.hygs.query.cap;

import de.oftik.hygs.contract.EntitySourceFixture;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.orm.cap.CategoryColumn;
import de.oftik.hygs.orm.cap.CategoryTable;
import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;

public class CategoryDAO extends AbstractDao<Category, CategoryTable> {
	static final EntitySourceFixture<Category, CategoryTable> SOURCE = new EntitySourceFixture<Category, CategoryTable>(
			CategoryTable.TABLE, CategoryColumn.cat_deleted);

	public CategoryDAO(ApplicationContext context) {
		super(context, SOURCE);
	}

	@Override
	protected Category instantiate() {
		return new Category();
	}

}
