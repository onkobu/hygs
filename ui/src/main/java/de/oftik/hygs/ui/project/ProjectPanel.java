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
import de.oftik.hygs.cmd.Notification;
import de.oftik.hygs.cmd.project.CapabilityAssigned;
import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.contract.CacheType;
import de.oftik.hygs.query.cap.Capability;
import de.oftik.hygs.query.cap.CapabilityDAO;
import de.oftik.hygs.query.company.Company;
import de.oftik.hygs.query.company.CompanyDAO;
import de.oftik.hygs.query.project.AssignedCapabilityDAO;
import de.oftik.hygs.query.project.Project;
import de.oftik.hygs.query.project.ProjectDAO;
import de.oftik.hygs.ui.ApplicationContext;
import de.oftik.hygs.ui.ContextEvent;
import de.oftik.hygs.ui.EntityCreateDialog;
import de.oftik.hygs.ui.EntityListPanel;

public class ProjectPanel extends EntityListPanel<Project, ProjectForm> implements CompanyCache, CapabilityCache {
	private final CompanyDAO companyDao;
	private final CapabilityDAO capabilityDao;
	private final Map<Long, Company> companyCache = new HashMap<>();
	private final Map<Long, Capability> capabilityCache = new HashMap<>();
	private final List<CacheListener> cacheListener = new ArrayList<>();

	public enum Cache implements CacheType {
		COMPANY, CAPABILITY;
	}

	public ProjectPanel(ApplicationContext applicationContext) {
		super(applicationContext, new ProjectDAO(applicationContext), new ProjectCellRenderer());
		this.companyDao = new CompanyDAO(applicationContext);
		this.capabilityDao = new CapabilityDAO(applicationContext);
		fillCache();
		final ProjectForm prjForm = getForm();
		prjForm.setCompanyCache(this);
		prjForm.setCapabilityCache(this);
		prjForm.setAssignedCapabilityDAO(new AssignedCapabilityDAO(applicationContext));
		broker().registerListener(
				new EntityNotificationListener<Project, ProjectForm>(CommandTargetDefinition.project, this));
		broker().registerListener(new AssignmentNotificationListener<CapabilityAssigned>(
				CommandTargetDefinition.project_capability, this::capabilityAssigned));
		broker().registerListener(new SubElementNotificationListener(CommandTargetDefinition.project_capability,
				this::capabilitySuccess, this::capabilityError));
	}

	public void capabilitySuccess(Notification not) {
		getForm().refreshCapabilities();
	}

	public void capabilityError(Notification not) {
	}

	public void capabilityAssigned(CapabilityAssigned notification) {
		getForm().refreshCapabilities();
	}

	@Override
	public ProjectForm createForm(Supplier<CommandBroker> brokerSupplier) {
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
		cacheListener.forEach((cl) -> cl.refresh(Cache.COMPANY));
		capabilityCache.clear();
		try {
			capabilityDao.consumeAll((cmp) -> capabilityCache.put(cmp.getId(), cmp));
		} catch (SQLException ex) {
			throw new IllegalStateException(ex);
		}
		cacheListener.forEach((cl) -> cl.refresh(Cache.CAPABILITY));
	}

	@Override
	public void createEntity(ActionEvent evt) {
		final EntityCreateDialog<Project, ProjectForm> dlg = wrapFormAsCreateDialog();
		dlg.getForm().setCompanyCache(this);
		dlg.getForm().setCapabilityCache(this);
		dlg.showAndWaitForDecision();
	}

	@Override
	public Company getCompanyById(long id) {
		return companyCache.get(id);
	}

	@Override
	public void consumeAllCompanies(Consumer<Company> consumer) {
		companyCache.forEach((key, value) -> consumer.accept(value));
	}

	@Override
	public Capability getCapabilityById(long id) {
		return capabilityCache.get(id);
	}

	@Override
	public void consumeAllCapabilities(Consumer<Capability> consumer) {
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
