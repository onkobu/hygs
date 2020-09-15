package de.oftik.hygs.ui.cap;

import java.util.Collection;
import java.util.Collections;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.query.cap.Category;
import de.oftik.hygs.query.cap.CategoryColumn;
import de.oftik.keyhs.kersantti.Column;
import de.oftik.keyhs.kersantti.Constraint;
import de.oftik.keyhs.kersantti.Table;

public class CategoryTable implements Table<Category>, EntitySource<Category> {
	public static final CategoryTable TABLE = new CategoryTable();

	private CategoryTable() {
	}

	@Override
	public Table<Category> getTable() {
		return this;
	}

	@Override
	public Column<Category> getDeleteColumn() {
		return CategoryColumn.cat_deleted;
	}

	@Override
	public String name() {
		return "cap_category";
	}

	@Override
	public Column<Category> getPkColumn() {
		return CategoryColumn.cat_id;
	}

	@Override
	public Column<Category>[] columns() {
		return CategoryColumn.values();
	}

	@Override
	public Collection<Constraint> constraints() {
		return Collections.emptyList();
	}
}
