package de.oftik.hygs.ui.company;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.oftik.hygs.query.company.Company;

public class CompanyCellRenderer implements ListCellRenderer<Company> {
	private final DefaultListCellRenderer wrapped = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList<? extends Company> lst, Company item, int arg2, boolean arg3,
			boolean arg4) {
		return wrapped.getListCellRendererComponent(lst, item.getName(), arg2, arg3, arg4);
	}

}
