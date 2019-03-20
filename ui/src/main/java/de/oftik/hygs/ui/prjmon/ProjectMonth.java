package de.oftik.hygs.ui.prjmon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectMonth {
	@XmlAttribute
	private long id;

	@XmlAttribute
	private String projectName;

	@XmlAttribute
	private int months;

	public ProjectMonth() {
		super();
	}

	public ProjectMonth(long id, String projectName, int months) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.months = months;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}
}
