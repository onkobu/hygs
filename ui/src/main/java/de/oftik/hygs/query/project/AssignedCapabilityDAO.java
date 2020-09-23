package de.oftik.hygs.query.project;

import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.kehys.kersantti.query.DAO;

public class AssignedCapabilityDAO extends DAO<AssignedCapability> {
	public AssignedCapabilityDAO(ApplicationContext context) {
		super(context, AssignedCapabilityTable.TABLE);
	}

	public List<AssignedCapability> findByProject(Project prj) throws SQLException {
		return findBy(AssignedCapabilityColumn.prj_id, prj.getId());
	}

	@Override
	protected AssignedCapability instantiate() {
		return new AssignedCapability();
	}
}
