package de.oftik.hygs.ui.project;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.query.project.ProjectDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityListPanel;

public class ProjectPanel extends EntityListPanel<Project> implements CompanyCache {
	private final CompanyDAO companyDao;
	private final Map<Long, Company> companyCache = new HashMap<>();

	public ProjectPanel(ApplicationContext applicationContext) {
		super(applicationContext, new ProjectDAO(applicationContext), new ProjectForm(applicationContext::getBroker),
				new ProjectCellRenderer());
		this.companyDao = new CompanyDAO(applicationContext);
		try {
			companyDao.consumeAll((cmp) -> companyCache.put(cmp.getId(), cmp));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		final ProjectForm prjForm = (ProjectForm) getForm();
		prjForm.setCompanyCache(this);
		prjForm.setAssignedCapabilityDAO(new AssignedCapabilityDAO(applicationContext));
	}

	@Override
	public Company getCompanyById(long id) {
		return companyCache.get(id);
	}

}
