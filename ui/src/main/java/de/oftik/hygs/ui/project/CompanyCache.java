package de.oftik.hygs.ui.project;

import java.util.function.Consumer;

import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.query.company.Company;
import de.oftik.kehys.kersantti.ForeignKey;

public interface CompanyCache {
	Company getCompany(ForeignKey<Company> companyKey);

	void consumeAllCompanies(Consumer<Company> consumer);

	void addCacheListener(CacheListener listener);

	void removeCacheListener(CacheListener listener);
}
