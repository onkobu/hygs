package de.oftik.hygs.cmd;

import de.oftik.hygs.cmd.cat.CategoryQueue;
import de.oftik.hygs.cmd.company.CompanyQueue;
import de.oftik.hygs.cmd.project.ProjectCapMappingQueue;
import de.oftik.hygs.cmd.project.ProjectQueue;
import de.oftik.hygs.query.Table;
import de.oftik.hygs.ui.ApplicationContext;

public enum CommandTargetDefinition implements CommandTarget {
	category(Table.cap_category) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CategoryQueue(ctx);
		}
	},

	company(Table.prj_company) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CompanyQueue(ctx);
		}
	},

	project(Table.cap_project) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new ProjectQueue(ctx);
		}
	},

	project_capability(Table.prj_cap_mapping) {

		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new ProjectCapMappingQueue(ctx);
		}
	};

	private final Table table;

	private CommandTargetDefinition(Table table) {
		this.table = table;
	}

	public abstract CommandQueue queue(ApplicationContext ctx);

	public static CommandTarget targetForTable(Table t) {
		for (CommandTargetDefinition dfn : values()) {
			if (dfn.table == t) {
				return dfn;
			}
		}
		return null;
	}
}
