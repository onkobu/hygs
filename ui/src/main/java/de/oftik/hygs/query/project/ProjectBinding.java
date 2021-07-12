package de.oftik.hygs.query.project;

import java.time.LocalDate;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.orm.project.ProjectTable;

public class ProjectBinding implements Identifiable<Project, ProjectTable>, MappableToString {
	private Project project;

	public ProjectBinding() {
	}

	public ProjectBinding(Project prj) {
		this.project = project;
	}

	public ProjectBinding(String id, String name, LocalDate from, LocalDate to, Company company, String description,
			int weight) {
		super();
	}

	@Override
	public String getId() {
		return project.getId();
	}

	@Override
	public EntitySource<Project, ProjectTable> getSource() {
		return ProjectDAO.SOURCE;
	}

	@Override
	public String toShortString() {
		return project.getName();
	}

	@Override
	public String toLongString() {
		return String.format("%s %td.%<tm.%<tY-%td.%<tm.%<tY", project.getName(), project.getFrom(), project.getTo());
	}

	public static ProjectBinding withId(String text) {
		final var prj = new Project();
		prj.setId(text);
		return new ProjectBinding(prj);
	}

	@Override
	public Project unwrap() {
		return project;
	}
}
