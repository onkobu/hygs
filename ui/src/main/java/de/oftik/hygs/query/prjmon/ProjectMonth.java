package de.oftik.hygs.query.prjmon;

import de.oftik.kehys.kersantti.AbstractIdentifiable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectMonth extends AbstractIdentifiable {
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String projectName;

	@XmlAttribute
	private int months;

	public ProjectMonth() {
	}

	public ProjectMonth(String id, String projectName, int months) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.months = months;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public int getMonths() {
		return months;
	}

	@Override
	public void createId() {
	}

	void setId(String id) {
		this.id = id;
	}

	void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	void setMonths(int months) {
		this.months = months;
	}
}
