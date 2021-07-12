package de.oftik.hygs.query.project;

import de.oftik.hygs.orm.cap.Capability;
import de.oftik.hygs.orm.project.Project;
import de.oftik.kehys.kersantti.AbstractIdentifiable;
import de.oftik.kehys.kersantti.ForeignKey;

/**
 * Weighted assignment of a capability to a project.
 *
 * @author onkobu
 *
 */
public class ProjectCapMapping extends AbstractIdentifiable {
	private ForeignKey<Project> project;
	private ForeignKey<Capability> capability;
	private int assignedWeight;

	protected ProjectCapMapping(Project prj, Capability cap, int assignedWeight) {
		super();
		this.project = new ForeignKey<>(prj);
		this.capability = new ForeignKey<>(cap);
		this.assignedWeight = assignedWeight;
	}

	@Override
	public void createId() {
	}

	@Override
	public String getId() {
		return String.format("%d-%d", getProject().getParentId(), getCapability().getParentId());
	}

	public ForeignKey<Project> getProject() {
		return project;
	}

	public ForeignKey<Capability> getCapability() {
		return capability;
	}

	void setProject(ForeignKey<Project> project) {
		this.project = project;
	}

	void setCapability(ForeignKey<Capability> capability) {
		this.capability = capability;
	}

	void setAssignedWeight(int assignedWeight) {
		this.assignedWeight = assignedWeight;
	}

	public int getAssignedWeight() {
		return assignedWeight;
	}

}
