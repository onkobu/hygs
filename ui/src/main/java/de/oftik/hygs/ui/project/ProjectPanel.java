package de.oftik.hygs.ui.project;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.EntityListPanel;
import de.oftik.hygs.ui.company.Company;
import de.oftik.hygs.ui.company.CompanyDAO;

public class ProjectPanel extends EntityListPanel<Project> implements CompanyCache {
	private final CompanyDAO companyDao;
	private final Map<Long, Company> companyCache = new HashMap<>();

	public ProjectPanel(ApplicationContext applicationContext) {
		super(applicationContext, new ProjectDAO(applicationContext), new ProjectForm(), new ProjectCellRenderer());
		this.companyDao = new CompanyDAO(applicationContext);
		try {
			companyDao.consumeAll((cmp) -> companyCache.put(cmp.getId(), cmp));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		((ProjectForm) getForm()).setCompanyCache(this);
	}

	@Override
	public Company getCompanyById(long id) {
		return companyCache.get(id);
	}

}
