package de.oftik.hygs.query.project;

public class AssignedCapability {
	private final long projectId;
	private final long capabilityId;
	private final int weight;
	private final String name;
	private final String version;

	public AssignedCapability(long projectId, long capabilityId, int weight, String name, String version) {
		super();
		this.projectId = projectId;
		this.capabilityId = capabilityId;
		this.weight = weight;
		this.name = name;
		this.version = version;
	}

	public long getProjectId() {
		return projectId;
	}

	public long getCapabilityId() {
		return capabilityId;
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

}
