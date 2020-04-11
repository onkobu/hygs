package de.oftik.hygs.ui.project;

import java.util.function.Consumer;

import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.query.company.Company;

public interface CompanyCache {
	Company getCompanyById(long id);

	void consumeAllCompanies(Consumer<Company> consumer);

	void addCacheListener(CacheListener listener);

	void removeCacheListener(CacheListener listener);
}
