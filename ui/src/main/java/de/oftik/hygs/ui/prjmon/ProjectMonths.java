package de.oftik.hygs.ui.prjmon;

import java.util.List;

import de.oftik.hygs.query.prjmon.ProjectMonth;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
