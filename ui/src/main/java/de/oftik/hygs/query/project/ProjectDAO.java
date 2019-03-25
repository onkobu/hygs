package de.oftik.hygs.query.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;

public class ProjectDAO extends DAO<Project> {
	public ProjectDAO(ApplicationContext context) {
		super(context, Table.cap_project);
	}

	@Override
	protected Project map(ResultSet rs) throws SQLException {
		return ProjectColumn.to(rs);
	}
}
