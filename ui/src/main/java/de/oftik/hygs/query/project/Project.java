package de.oftik.hygs.query.project;

import java.time.LocalDate;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.query.Table;

public class Project implements Identifiable, MappableToString {
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
	public EntitySource getSource() {
		return Table.cap_project;
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

	@Override
	public String toShortString() {
		return getName();
	}

	@Override
	public String toLongString() {
		return String.format("%s %td.%<tm.%<tY-%td.%<tm.%<tY", getName(), getFrom(), getTo());
	}
}
