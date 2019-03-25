package de.oftik.hygs.ui.project;

import de.oftik.hygs.query.company.Company;

public interface CompanyCache {
	Company getCompanyById(long id);
}
