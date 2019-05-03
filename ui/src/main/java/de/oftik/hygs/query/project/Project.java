package de.oftik.hygs.query.project;

import java.time.LocalDate;

import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.query.Table;

public class Project implements Identifiable {
	private final long id;
	private final String name;
	private final LocalDate from;
	private final LocalDate to;
	private final long companyId;
	private final String description;
	private final int weight;

	public Project(long id, String name, LocalDate from, LocalDate to, long companyId, String description, int weight) {
		super();
		this.id = id;
		this.name = name;
		this.from = from;
		this.to = to;
		this.companyId = companyId;
		this.description = description;
		this.weight = weight;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getSource() {
		return Table.cap_project.name();
	}

	public String getName() {
		return name;
	}

	public LocalDate getFrom() {
		return from;
	}

	public LocalDate getTo() {
		return to;
	}

	public long getCompanyId() {
		return companyId;
	}

	public String getDescription() {
		return description;
	}

	public int getWeight() {
		return weight;
	}
}
