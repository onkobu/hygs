package de.oftik.hygs.ui;

import java.util.ResourceBundle;

public enum I18N {
	COMPANY,

	ID,

	STREET,

	CITY,

	ZIP,

	EXPORT_PRJ_MONTHS,

	EXPORT,

	MSG_EXPORTED_XML,

	MSG_EXPORTED_STATISTICS,

	PROJECT,

	FROM,

	TO,

	DESCRIPTION,

	CAPABILITY,

	VERSION,

	CATEGORY,

	WEIGHT;

	public String title() {
		return getBundle().getString(name() + ".title");
	}

	public String label() {
		return getBundle().getString(name() + ".label");
	}

	private ResourceBundle getBundle() {
		return ResourceBundle.getBundle(I18N.class.getName().toLowerCase());
	}

	public boolean hasTooltip() {
		return getBundle().containsKey(createTooltipKey());
	}

	private String createTooltipKey() {
		return name() + ".tooltip";
	}

	public String tooltip() {
		return getBundle().getString(createTooltipKey());
	}

	public String message(Object... params) {
		final String rawMsg = getBundle().getString(name() + ".message");
		if (params == null || params.length == 0) {
			return rawMsg;
		}
		return String.format(rawMsg, params);
	}
}
