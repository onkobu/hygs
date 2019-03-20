package de.oftik.hygs.ui.prjmon;

import java.sql.ResultSet;
import java.sql.SQLException;

public enum ProjectMonthColumn {
	prj_id {
		@Override
		public void toInternal(ResultSet rs, ProjectMonth prjMon) throws SQLException {
			prjMon.setId(asLong(rs));
		}
	},
	prj_name {
		@Override
		public void toInternal(ResultSet rs, ProjectMonth prjMon) throws SQLException {
			prjMon.setProjectName(asString(rs));
		}
	},
	prj_months {
		@Override
		public void toInternal(ResultSet rs, ProjectMonth prjMon) throws SQLException {
			prjMon.setMonths(asInt(rs));
		}
	};

	abstract void toInternal(ResultSet rs, ProjectMonth prjMon) throws SQLException;

	String asString(ResultSet rs) throws SQLException {
		return rs.getString(name());
	}

	long asLong(ResultSet rs) throws SQLException {
		return rs.getLong(name());
	}

	int asInt(ResultSet rs) throws SQLException {
		return rs.getInt(name());
	}

	public static ProjectMonth to(ResultSet rs, ProjectMonth prjMon) throws SQLException {
		for (ProjectMonthColumn col : values()) {
			col.toInternal(rs, prjMon);
		}
		return prjMon;
	}
}
