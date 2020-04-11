package de.oftik.hygs.query.project;

/**
 * Weighted assignment of a capability to a project.
 * 
 * @author onkobu
 *
 */
public class ProjectCapMapping {
	private final long prjId;
	private final long capId;
	private final int assignedWeight;

	protected ProjectCapMapping(long prjId, long capId, int assignedWeight) {
		super();
		this.prjId = prjId;
		this.capId = capId;
		this.assignedWeight = assignedWeight;
	}

	public long getPrjId() {
		return prjId;
	}

	public long getCapId() {
		return capId;
	}

	public int getAssignedWeight() {
		return assignedWeight;
	}

}
