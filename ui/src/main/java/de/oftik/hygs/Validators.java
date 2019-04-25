package de.oftik.hygs;

import java.io.File;

public class Validators {
	public static boolean isValidPath(String str) {
		if (str == null) {
			return false;
		}

		if (str.length() == 0 || str.trim().length() == 0) {
			return false;
		}
		final File f = new File(str);

		return f.canRead();
	}
}
