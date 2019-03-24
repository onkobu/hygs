package de.oftik.hygs.ui.project;

public class AssignedCapability {
	private long projectId;
	private long capabilityId;
	private int weight;
	private String name;
	private String version;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getCapabilityId() {
		return capabilityId;
	}

	public void setCapabilityId(long capabilityId) {
		this.capabilityId = capabilityId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
