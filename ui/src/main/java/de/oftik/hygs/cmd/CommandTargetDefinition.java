package de.oftik.hygs.cmd;

import de.oftik.hygs.cmd.cat.CategoryQueue;
import de.oftik.hygs.cmd.company.CompanyQueue;
import de.oftik.hygs.cmd.project.ProjectQueue;
import de.oftik.hygs.ui.ApplicationContext;

public enum CommandTargetDefinition implements CommandTarget {
	category {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CategoryQueue(ctx);
		}
	},

	company {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new CompanyQueue(ctx);
		}
	},

	project {
		@Override
		public CommandQueue queue(ApplicationContext ctx) {
			return new ProjectQueue(ctx);
		}
	};

	public abstract CommandQueue queue(ApplicationContext ctx);
}
