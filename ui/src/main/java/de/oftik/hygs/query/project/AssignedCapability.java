package de.oftik.hygs.query.project;

import de.oftik.hygs.query.cap.Capability;
import de.oftik.kehys.kersantti.AbstractIdentifiable;
import de.oftik.kehys.kersantti.ForeignKey;

public class AssignedCapability extends AbstractIdentifiable {
	private String id;
	private ForeignKey<Project> project;
	private ForeignKey<Capability> capability;
	private int weight;
	private String name;
	private String version;

	AssignedCapability() {
	}

	public AssignedCapability(Project project, Capability capability, int weight, String name, String version) {
		super();
		this.project = new ForeignKey<>(project);
		this.capability = new ForeignKey<>(capability);
		this.weight = weight;
		this.name = name;
		this.version = version;
	}

	public String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	void setProject(ForeignKey<Project> project) {
		this.project = project;
	}

	void setCapability(ForeignKey<Capability> capability) {
		this.capability = capability;
	}

	void setWeight(int weight) {
		this.weight = weight;
	}

	void setName(String name) {
		this.name = name;
	}

	void setVersion(String version) {
		this.version = version;
	}

	public ForeignKey<Project> getProject() {
		return project;
	}

	public ForeignKey<Capability> getCapability() {
		return capability;
	}

	public int getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public void createId() {

	}

}
