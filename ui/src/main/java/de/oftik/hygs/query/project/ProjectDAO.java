package de.oftik.hygs.query.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.AbstractDao;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.keyhs.kersantti.Column;

public class ProjectDAO extends AbstractDao<Project> {
	public ProjectDAO(ApplicationContext context) {
		super(context, Table.cap_project);
	}

	@Override
	protected Project map(ResultSet rs) throws SQLException {
		return ProjectColumn.to(rs);
	}

	@Override
	protected Column<?> getPkColumn() {
		return ProjectColumn.prj_id;
	}

	@Override
	protected Column<?> getDeletedColumn() {
		return ProjectColumn.prj_deleted;
	}
}
