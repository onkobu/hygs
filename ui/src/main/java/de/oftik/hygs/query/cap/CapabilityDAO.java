package de.oftik.hygs.query.cap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.cap.Category;

public class CapabilityDAO extends DAO<Capability> {
	public CapabilityDAO(ApplicationContext context) {
		super(context, Table.cap_capability);
	}

	@Override
	protected Capability map(ResultSet rs) throws SQLException {
		return CapabilityColumn.to(rs);
	}

	public List<Capability> findByCategory(Category g) throws SQLException {
		try (Connection conn = createConnection()) {
			return super.findBy(conn, CapabilityColumn.cap_category, g.getId());
		}
	}

	@Override
	protected Column<?> getPkColumn() {
		return CapabilityColumn.cap_id;
	}

	@Override
	protected Column<?> getDeletedColumn() {
		return CapabilityColumn.cap_deleted;
	}
}
