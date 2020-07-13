package de.oftik.hygs.query.project;

import java.time.LocalDate;

import de.oftik.hygs.contract.EntitySource;
import de.oftik.hygs.contract.Identifiable;
import de.oftik.hygs.contract.MappableToString;
import de.oftik.hygs.query.company.Company;
import de.oftik.keyhs.kersantti.ForeignKey;

public class Project implements Identifiable, MappableToString {
	private String id;
	private String name;
	private LocalDate from;
	private LocalDate to;
	private ForeignKey<Company> company;
	private String description;
	private int weight;
	private boolean deleted;

	Project() {
	}

	public Project(String id, String name, LocalDate from, LocalDate to, Company company, String description,
			int weight) {
		super();
		this.id = id;
		this.name = name;
		this.from = from;
		this.to = to;
		this.company = new ForeignKey<>(company);
		this.description = description;
		this.weight = weight;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public EntitySource getSource() {
		return ProjectTable.TABLE;
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

	public ForeignKey<Company> getCompany() {
		return company;
	}

	Project setId(String id) {
		this.id = id;
		return this;
	}

	void setName(String name) {
		this.name = name;
	}

	void setFrom(LocalDate from) {
		this.from = from;
	}

	public boolean isDeleted() {
		return deleted;
	}

	void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	void setTo(LocalDate to) {
		this.to = to;
	}

	void setCompany(ForeignKey<Company> company) {
		this.company = company;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setWeight(int weight) {
		this.weight = weight;
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

	public static Project withId(String text) {
		return new Project().setId(text);
	}
}
