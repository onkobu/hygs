package de.oftik.hygs.query.prjmon;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.keyhs.kersantti.query.DAO;

public class ProjectMonthsDAO extends DAO<ProjectMonth> {

	public ProjectMonthsDAO(ApplicationContext context) {
		super(context, ProjectMonthTable.TABLE);
	}

	@Override
	protected ProjectMonth instantiate() {
		return new ProjectMonth();
	}
}
