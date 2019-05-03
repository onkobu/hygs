package de.oftik.hygs.query.prjmon;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.query.Column;
import de.oftik.hygs.query.DAO;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.query.project.ProjectColumn;
import de.oftik.hygs.ui.ApplicationContext;

public class ProjectMonthsDAO extends DAO<ProjectMonth> {

	public ProjectMonthsDAO(ApplicationContext context) {
		super(context, Table.prj_months);
	}

	@Override
	protected ProjectMonth map(ResultSet rs) throws SQLException {
		return ProjectMonthColumn.to(rs);
	}

	@Override
	protected Column<?> getPkColumn() {
		return ProjectColumn.prj_id;
	}

	@Override
	protected Column<?> getDeletedColumn() {
		throw new UnsupportedOperationException();
	}
}
