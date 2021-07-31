package de.oftik.hygs.ui.project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.oftik.hygs.cmd.CommandBroker;
import de.oftik.hygs.cmd.CommandTargetDefinition;
import de.oftik.hygs.cmd.EntityResurrected;
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.NotificationType;
import de.oftik.hygs.cmd.company.CompanyCreated;
import de.oftik.hygs.cmd.company.CompanyDeleted;
import de.oftik.hygs.cmd.company.CompanySaved;
import de.oftik.hygs.cmd.project.CapabilityAssigned;
import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.contract.CacheType;
import de.oftik.hygs.orm.cap.CapabilityColumn;
import de.oftik.hygs.orm.cap.Category;
import de.oftik.hygs.orm.cap.CategoryColumn;
import de.oftik.hygs.orm.company.Company;
import de.oftik.hygs.orm.company.CompanyColumn;
import de.oftik.hygs.orm.project.Project;
import de.oftik.hygs.orm.project.ProjectColumn;
import de.oftik.hygs.orm.project.ProjectTable;
import de.oftik.hygs.query.cap.CapabilityDAO;
import de.oftik.hygs.query.cap.CategoryDAO;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.ProjectDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ContextEvent;
import de.oftik.hygs.ui.EntityListPanel;
import de.oftik.kehys.kersantti.ForeignKey;
import de.oftik.kehys.kersantti.query.QueryOperators;

public class ProjectPanel extends EntityListPanel<Project, ProjectTable, ProjectForm>
		implements CompanyCache, CapabilityCache {
	private final CompanyDAO companyDao;
	private final CapabilityDAO capabilityDao;
	private final CategoryDAO categoryDao;

	private final Map<String, Company> companyCache = new HashMap<>();
	private final Map<String, CapabilityWithCategory> capabilityCache = new HashMap<>();
	private final Map<String, Category> categoryCache = new HashMap<>();
	private final List<CacheListener> cacheListener = new ArrayList<>();

	public enum Cache implements CacheType {
		COMPANY, CAPABILITY;
	}

	public ProjectPanel(ApplicationContext applicationContext) {
		super(applicationContext, new ProjectDAO(applicationContext), new ProjectCellRenderer(),
				ProjectColumn.prj_deleted, ProjectColumn.prj_name);
		this.companyDao = new CompanyDAO(applicationContext);
		this.capabilityDao = new CapabilityDAO(applicationContext);
		this.categoryDao = new CategoryDAO(applicationContext);
		fillCache();
		final ProjectForm prjForm = getForm();
		prjForm.setCompanyCache(this);
		prjForm.setCapabilityCache(this);
		prjForm.setAssignedCapabilityDAO(new AssignedCapabilityDAO(applicationContext));

		broker().registerListener(new AnyNotificationListener<CompanyCreated>(CommandTargetDefinition.company,
				this::companyCreated, NotificationType.INSERT));
		broker().registerListener(new AnyNotificationListener<EntityResurrected>(CommandTargetDefinition.company,
				this::companyResurrected, NotificationType.RESURRECT));

		broker().registerListener(new AnyNotificationListener<CompanyDeleted>(CommandTargetDefinition.company,
				this::companyDeleted, NotificationType.DELETE, NotificationType.TRASHED));
		broker().registerListener(new AnyNotificationListener<CompanyDeleted>(CommandTargetDefinition.company,
				this::companyDeleted, NotificationType.TRASHED));
		broker().registerListener(new AnyNotificationListener<CompanySaved>(CommandTargetDefinition.company,
				this::companySaved, NotificationType.UPDATE));

		broker().registerListener(new EntityNotificationListener<Project, ProjectTable, ProjectForm>(
				CommandTargetDefinition.project, this));
		broker().registerListener(new AnyNotificationListener<CapabilityAssigned>(
				CommandTargetDefinition.project_capability, this::capabilityAssigned));
		broker().registerListener(new SubElementNotificationListener(CommandTargetDefinition.project_capability,
				this::capabilitySuccess, this::capabilityError));
	}

	public void capabilitySuccess(Notification not) {
		getForm().refreshCapabilities();
	}

	public void capabilityError(Notification not) {
	}

	public void companyCreated(CompanyCreated notification) {
		try {
			final var cmp = companyDao.findById(notification.getIds().get(0));
			cacheCompany(cmp.get());
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void companyResurrected(EntityResurrected notification) {
		try {
			final var cmp = companyDao.findById(notification.getIds().get(0));
			cacheCompany(cmp.get());
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private void cacheCompany(final Company company) {
		companyCache.put(company.getId(), company);
		cacheListener.forEach((cl) -> cl.refresh(Cache.COMPANY));
	}

	public void companySaved(CompanySaved notification) {
		try {
			final var cmp = companyDao.findById(notification.getIds().get(0));
			cacheCompany(cmp.get());
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void companyDeleted(CompanyDeleted notification) {
		companyCache.remove(notification.getIds().get(0));
		cacheListener.forEach((cl) -> cl.refresh(Cache.COMPANY));
	}

	public void capabilityAssigned(CapabilityAssigned notification) {
		getForm().refreshCapabilities();
	}

	@Override
	public ProjectForm createForm(Supplier<CommandBroker> brokerSupplier) {
		return new ProjectForm(this, brokerSupplier);
	}

	@Override
	public void onEvent(ContextEvent e) {
		super.onEvent(e);
		fillCache();
	}

	private void fillCache() {
		companyCache.clear();
		try {
			companyDao.consumeWhere(QueryOperators.columnIs(CompanyColumn.cmp_deleted, false), null,
					(cmp) -> companyCache.put(cmp.getId(), cmp));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		categoryCache.clear();
		try {
			categoryDao.consumeWhere(QueryOperators.columnIs(CategoryColumn.cat_deleted, false), null,
					(cat) -> categoryCache.put(cat.getId(), cat));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		cacheListener.forEach((cl) -> cl.refresh(Cache.COMPANY));
		capabilityCache.clear();
		try {
			capabilityDao.consumeWhere(QueryOperators.columnIs(CapabilityColumn.cap_deleted, false), null,
					(cap) -> capabilityCache.put(cap.getId(),
							new CapabilityWithCategory(cap, categoryCache.get(cap.getCategoryId().getParentId()))));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		cacheListener.forEach((cl) -> cl.refresh(Cache.CAPABILITY));
	}

	@Override
	public Company getCompany(ForeignKey<Company> companyKey) {
		return companyCache.get(companyKey.getParentId());
	}

	@Override
	public void consumeAllCompanies(Consumer<Company> consumer) {
		companyCache.forEach((key, value) -> consumer.accept(value));
	}

	@Override
	public CapabilityWithCategory getCapabilityById(String id) {
		return capabilityCache.get(id);
	}

	@Override
	public void consumeAllCapabilities(Consumer<CapabilityWithCategory> consumer) {
		capabilityCache.forEach((key, value) -> consumer.accept(value));
	}

	@Override
	public void addCacheListener(CacheListener listener) {
		if (cacheListener.contains(listener)) {
			return;
		}
		cacheListener.add(listener);
	}

	@Override
	public void removeCacheListener(CacheListener listener) {
		cacheListener.remove(listener);
	}
}
