package de.oftik.hygs.ui.prjmon;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.orm.Column;

public enum ProjectMonthColumn implements Column<ProjectMonth> {
	prj_id {
		@Override
		public void toPojo(ResultSet rs, ProjectMonth prjMon) throws SQLException {
			prjMon.setId(asLong(rs));
		}
	},
	prj_name {
		@Override
		public void toPojo(ResultSet rs, ProjectMonth prjMon) throws SQLException {
			prjMon.setProjectName(asString(rs));
		}
	},
	prj_months {
		@Override
		public void toPojo(ResultSet rs, ProjectMonth prjMon) throws SQLException {
			prjMon.setMonths(asInt(rs));
		}
	};

	public static ProjectMonth to(ResultSet rs, ProjectMonth prjMon) throws SQLException {
		for (ProjectMonthColumn col : values()) {
			col.toPojo(rs, prjMon);
		}
		return prjMon;
	}
}
