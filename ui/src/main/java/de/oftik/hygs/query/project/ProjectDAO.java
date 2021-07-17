package de.oftik.hygs.query.project;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.EntitySourceFixture;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.orm.project.ProjectColumn;
import de.oftik.hygs.orm.project.ProjectTable;
import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;

public class ProjectDAO extends AbstractDao<Project, ProjectTable> {
	private static final ProjectTable TABLE = new ProjectTable();
	public static final EntitySource<Project, ProjectTable> SOURCE = new EntitySourceFixture<>(TABLE,
			ProjectColumn.prj_deleted);

	public ProjectDAO(ApplicationContext context) {
		super(context, SOURCE);
	}

	@Override
	protected Project instantiate() {
		return new Project();
	}
}
