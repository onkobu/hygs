package de.oftik.hygs.ui;

import javax.swing.JTextField;

public final class TextFields {
	private TextFields() {
		// not to be instantiated
	}

	public static boolean isBlank(JTextField field) {
		return field.getText() == null || field.getText().isBlank();
	}
}