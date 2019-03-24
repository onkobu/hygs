package de.oftik.hygs.ui.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Table;

public class AssignedCapabilityDAO extends DAO<AssignedCapability> {
	public AssignedCapabilityDAO(ApplicationContext context) {
		super(context, Table.v_prj_cap);
	}

	List<AssignedCapability> findByProject(Project prj) throws SQLException {
		try (Connection conn = createConnection()) {
			return findBy(conn, AssignedCapabilityColumn.prj_id, prj.getId());
		}
	}

	@Override
	protected AssignedCapability map(ResultSet rs) throws SQLException {
		return AssignedCapabilityColumn.to(rs, new AssignedCapability());
	}
}
