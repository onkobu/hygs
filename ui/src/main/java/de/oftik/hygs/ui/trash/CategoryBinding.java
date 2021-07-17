package de.oftik.hygs.ui.trash;

import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.orm.cap.CategoryColumn;
import de.oftik.hygs.orm.cap.CategoryTable;

public class CategoryBinding extends AbstractBinding<Category, CategoryTable> {

	CategoryBinding(Category c) {
		super(c, CategoryTable.TABLE, CategoryColumn.cat_deleted);
	}

	@Override
	public String toString() {
		return "Category: " + getIdentifiable().getName();
	}

}
