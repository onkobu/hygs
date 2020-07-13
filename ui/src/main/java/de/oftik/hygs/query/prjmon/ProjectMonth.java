package de.oftik.hygs.query.prjmon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.oftik.keyhs.kersantti.Identifiable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectMonth implements Identifiable {
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

	public String getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public int getMonths() {
		return months;
	}

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
