package de.oftik.hygs.ui.trash;

import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.orm.project.ProjectColumn;
import de.oftik.hygs.orm.project.ProjectTable;

class ProjectBinding extends AbstractBinding<Project, ProjectTable> {
	ProjectBinding(Project p) {
		super(p, ProjectTable.TABLE, ProjectColumn.prj_deleted);
	}

	@Override
	public String toString() {
		return "Project: " + getIdentifiable().getName();
	}
}
