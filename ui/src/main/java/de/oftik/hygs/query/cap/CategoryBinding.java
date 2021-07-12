package de.oftik.hygs.query.cap;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.orm.cap.CategoryTable;

public class CategoryBinding implements Identifiable<Category, CategoryTable>, MappableToString {
	private Category category;

	public CategoryBinding(Category category) {
		this.category = category;
	}

	@Override
	public String toShortString() {
		return category.getName();
	}

	@Override
	public String toLongString() {
		return category.getName();
	}

	@Override
	public EntitySource<Category, CategoryTable> getSource() {
		return CategoryDAO.SOURCE;
	}

	@Override
	public String getId() {
		return category.getId();
	}

	@Override
	public Category unwrap() {
		return category;
	}

}
