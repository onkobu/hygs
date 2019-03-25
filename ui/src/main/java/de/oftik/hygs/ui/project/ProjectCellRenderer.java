package de.oftik.hygs.ui.project;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.oftik.hygs.query.project.Project;

public class ProjectCellRenderer implements ListCellRenderer<Project> {
	private final DefaultListCellRenderer wrapped = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList<? extends Project> lst, Project item, int arg2, boolean arg3,
			boolean arg4) {
		return wrapped.getListCellRendererComponent(lst, item.getName(), arg2, arg3, arg4);
	}

}
