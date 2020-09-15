package de.oftik.hygs.query.cap;

import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.keyhs.kersantti.Column;

public class CapabilityDAO extends AbstractDao<Capability> {
	public CapabilityDAO(ApplicationContext context) {
		super(context, CapabilityTable.TABLE);
	}

	public List<Capability> findByCategory(Category g) throws SQLException {
		return super.findBy(CapabilityColumn.cap_category, g.getId());
	}

	protected Column<?> getDeletedColumn() {
		return CapabilityColumn.cap_deleted;
	}

	@Override
	protected Capability instantiate() {
		return new Capability();
	}
}
