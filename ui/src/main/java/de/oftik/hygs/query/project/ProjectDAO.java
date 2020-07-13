package de.oftik.hygs.query.project;

import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.ui.ApplicationContext;

public class ProjectDAO extends AbstractDao<Project> {
	public ProjectDAO(ApplicationContext context) {
		super(context, ProjectTable.TABLE);
	}

	@Override
	protected Project instantiate() {
		return new Project();
	}
}
