package de.oftik.hygs.ui.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Table;

public class ProjectDAO extends DAO<Project> {
	public ProjectDAO(ApplicationContext context) {
		super(context, Table.cap_project);
	}

	@Override
	protected Project map(ResultSet rs) throws SQLException {
		return ProjectColumn.to(rs, new Project());
	}
}
