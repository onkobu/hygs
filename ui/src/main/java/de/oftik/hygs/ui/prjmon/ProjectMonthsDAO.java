package de.oftik.hygs.ui.prjmon;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.orm.DAO;
import de.oftik.hygs.ui.orm.Table;

public class ProjectMonthsDAO extends DAO<ProjectMonth> {

	public ProjectMonthsDAO(ApplicationContext context) {
		super(context, Table.prj_months);
	}

	@Override
	protected ProjectMonth map(ResultSet rs) throws SQLException {
		return ProjectMonthColumn.to(rs, new ProjectMonth());
	}

}
