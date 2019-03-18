package de.oftik.hygs.ui;

public enum I18N {
	COMPANY, ID, STREET, CITY, ZIP;

	public String title() {
		return name();
	}

	public String label() {
		return name();
	}
}
