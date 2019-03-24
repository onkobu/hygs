package de.oftik.hygs.ui.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.oftik.hygs.ui.orm.Column;

public enum ProjectColumn implements Column<Project> {
	prj_name {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setName(asString(rs));
		}
	},
	prj_from {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setFrom(asLocalDate(rs));
		}
	},
	prj_to {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setTo(asLocalDate(rs));
		}
	},
	prj_company {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setCompanyId(asLong(rs));
		}
	},
	prj_id {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setId(asLong(rs));
		}
	},
	prj_description {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setDescription(asString(rs));
		}
	},
	prj_weight {
		@Override
		public void toPojo(ResultSet rs, Project t) throws SQLException {
			t.setWeight(asInt(rs));
		}
	};

	public static Project to(ResultSet rs, Project prj) throws SQLException {
		for (ProjectColumn col : values()) {
			col.toPojo(rs, prj);
		}
		return prj;
	}
}
