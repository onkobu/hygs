package de.oftik.hygs.query.cap;

import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.EntitySourceFixture;
import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.cap.CapabilityColumn;
import de.oftik.hygs.orm.cap.CapabilityTable;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.kehys.kersantti.Column;

public class CapabilityDAO extends AbstractDao<Capability, CapabilityTable> {
	public static final EntitySource<Capability, CapabilityTable> SOURCE = new EntitySourceFixture<Capability, CapabilityTable>(
			CapabilityTable.TABLE, CapabilityColumn.cap_deleted);

	public CapabilityDAO(ApplicationContext context) {
		super(context, SOURCE);
	}

	public List<Capability> findByCategory(Category g) throws SQLException {
		return super.findBy(CapabilityColumn.cap_category_id, g.getId());
	}

	protected Column<?> getDeletedColumn() {
		return CapabilityColumn.cap_deleted;
	}

	@Override
	protected Capability instantiate() {
		return new Capability();
	}

}
