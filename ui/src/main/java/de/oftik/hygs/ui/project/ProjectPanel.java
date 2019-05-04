package de.oftik.hygs.ui.project;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.query.project.ProjectDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ContextEvent;
import de.oftik.hygs.ui.EntityForm;
import de.oftik.hygs.ui.EntityListPanel;

public class ProjectPanel extends EntityListPanel<Project> implements CompanyCache {
	private final CompanyDAO companyDao;
	private final Map<Long, Company> companyCache = new HashMap<>();
	private final List<CacheListener> cacheListener = new ArrayList<>();

	public ProjectPanel(ApplicationContext applicationContext) {
		super(applicationContext, new ProjectDAO(applicationContext), new ProjectCellRenderer());
		this.companyDao = new CompanyDAO(applicationContext);
		fillCache();
		final ProjectForm prjForm = (ProjectForm) getForm();
		prjForm.setCompanyCache(this);
		prjForm.setAssignedCapabilityDAO(new AssignedCapabilityDAO(applicationContext));
		broker().registerListener(new EntityNotificationListener<Project>(CommandTargetDefinition.project, this));
	}

	@Override
	public EntityForm<Project> createForm(Supplier<CommandBroker> brokerSupplier) {
		return new ProjectForm(brokerSupplier);
	}

	@Override
	public void onEvent(ContextEvent e) {
		super.onEvent(e);
		fillCache();
	}

	private void fillCache() {
		companyCache.clear();
		try {
			companyDao.consumeAll((cmp) -> companyCache.put(cmp.getId(), cmp));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		cacheListener.forEach(CacheListener::refresh);
	}

	@Override
	public void createEntity(ActionEvent evt) {
		wrapFormAsCreateDialog().showAndWaitForDecision();
	}

	@Override
	public Company getCompanyById(long id) {
		return companyCache.get(id);
	}

	@Override
	public void consumeAll(Consumer<Company> consumer) {
		companyCache.forEach((key, value) -> consumer.accept(value));
	}

	@Override
	public void addCacheListener(CacheListener listener) {
		cacheListener.add(listener);
	}
}
