package de.oftik.hygs.contract;

/**
 * Contract for something to be rendered as String in short and long form.
 * 
 * @author onkobu
 *
 */
public interface MappableToString {
	/**
	 * 
	 * @return A sort of title, label, fitting into a list or onto a button, e.g.
	 *         less than 15 characters.
	 */
	String toShortString();

	/**
	 * 
	 * @return A descriptive text for a text box but significantly less than 4GByte.
	 */
	String toLongString();
}
