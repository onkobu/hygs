package de.oftik.hygs.query.prjmon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectMonth {
	@XmlAttribute
	private final long id;

	@XmlAttribute
	private final String projectName;

	@XmlAttribute
	private final int months;

	public ProjectMonth() {
		throw new UnsupportedOperationException("Utterwise JAX-B whines like likkel gjal");
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

	public String getProjectName() {
		return projectName;
	}

	public int getMonths() {
		return months;
	}
}
