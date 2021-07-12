package de.oftik.hygs.query.project;

import java.sql.SQLException;
import java.util.List;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.EntitySourceFixture;
import de.oftik.hygs.orm.project.CapabilityInProject;
import de.oftik.hygs.orm.project.CapabilityInProjectColumn;
import de.oftik.hygs.orm.project.CapabilityInProjectTable;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.kehys.kersantti.query.OrMappableDAO;

public class AssignedCapabilityDAO extends OrMappableDAO<CapabilityInProject> {

	public static final EntitySource<CapabilityInProject, CapabilityInProjectTable> SOURCE = new EntitySourceFixture<CapabilityInProject, CapabilityInProjectTable>(
			CapabilityInProjectTable.TABLE, null);

	public AssignedCapabilityDAO(ApplicationContext context) {
		super(context, CapabilityInProjectTable.TABLE);
	}

	public List<CapabilityInProject> findByProject(Project prj) throws SQLException {
		return findBy(CapabilityInProjectColumn.assc_project_id, prj.getId());
	}

	@Override
	protected CapabilityInProject instantiate() {
		return new CapabilityInProject();
	}
}
