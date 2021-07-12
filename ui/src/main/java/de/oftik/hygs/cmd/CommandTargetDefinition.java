package de.oftik.hygs.cmd;

import de.oftik.hygs.cmd.cap.CapabilityQueue;
import de.oftik.hygs.cmd.cat.CategoryQueue;
import de.oftik.hygs.cmd.company.CompanyQueue;
import de.oftik.hygs.cmd.project.ProjectCapMappingQueue;
import de.oftik.hygs.cmd.project.ProjectQueue;
import de.oftik.hygs.orm.cap.CapabilityTable;
import de.oftik.hygs.orm.cap.CategoryTable;
import de.oftik.hygs.orm.company.CompanyTable;
import de.oftik.hygs.orm.project.ProjectTable;
import de.oftik.hygs.query.project.ProjectCapTable;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.kehys.kersantti.Table;

public enum CommandTargetDefinition implements CommandTarget {
	category(CategoryTable.TABLE) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CategoryQueue(ctx);
		}
	},

	company(CompanyTable.TABLE) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CompanyQueue(ctx);
		}
	},

	project(ProjectTable.TABLE) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new ProjectQueue(ctx);
		}
	},

	project_capability(ProjectCapTable.TABLE) {

		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new ProjectCapMappingQueue(ctx);
		}
	},

	capability(CapabilityTable.TABLE) {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CapabilityQueue(ctx);
		}
	};

	private final Table<?> table;

	CommandTargetDefinition(Table<?> table) {
		this.table = table;
	}

	public abstract CommandQueue queue(ApplicationContext ctx);

	public static CommandTarget targetForTable(Table<?> t) {
		for (CommandTargetDefinition dfn : values()) {
			if (dfn.table == t) {
				return dfn;
			}
		}
		return null;
	}
}
