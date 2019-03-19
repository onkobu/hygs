package de.oftik.hygs.ui;

import java.util.ResourceBundle;

public enum I18N {
	COMPANY,

	ID,

	STREET,

	CITY,

	ZIP;

	public String title() {
		return getBundle().getString(name() + ".title");
	}

	public String label() {
		return getBundle().getString(name() + ".label");
	}

	private ResourceBundle getBundle() {
		return ResourceBundle.getBundle(I18N.class.getName().toLowerCase());
	}
}
