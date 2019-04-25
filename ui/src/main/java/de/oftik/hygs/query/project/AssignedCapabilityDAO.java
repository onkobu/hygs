package de.oftik.hygs.query.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;

public class AssignedCapabilityDAO extends DAO<AssignedCapability> {
	public AssignedCapabilityDAO(ApplicationContext context) {
		super(context, Table.v_prj_cap);
	}

	public List<AssignedCapability> findByProject(Project prj) throws SQLException {
		try (Connection conn = createConnection()) {
			return findBy(conn, AssignedCapabilityColumn.prj_id, prj.getId());
		}
	}

	@Override
	protected AssignedCapability map(ResultSet rs) throws SQLException {
		return AssignedCapabilityColumn.to(rs);
	}

	@Override
	protected Column<?> getPkColumn() {
		return AssignedCapabilityColumn.cap_id;
	}
}
