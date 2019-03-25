package de.oftik.hygs.ui.prjmon;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.oftik.hygs.query.prjmon.ProjectMonth;

@XmlRootElement
public class ProjectMonths {
	private List<ProjectMonth> months;

	@XmlElement
	public List<ProjectMonth> getMonths() {
		return months;
	}

	public void setMonths(List<ProjectMonth> months) {
		this.months = months;
	}
}
