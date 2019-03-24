package de.oftik.hygs.ui.cap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Table;

public class CapabilityDAO extends DAO<Capability> {
	public CapabilityDAO(ApplicationContext context) {
		super(context, Table.cap_capability);
	}

	@Override
	protected Capability map(ResultSet rs) throws SQLException {
		return CapabilityColumn.to(rs, new Capability());
	}

	public List<Capability> findByCategory(Category g) throws SQLException {
		try (Connection conn = createConnection()) {
			return super.findBy(conn, CapabilityColumn.cap_category, g.getId());
		}
	}
}
