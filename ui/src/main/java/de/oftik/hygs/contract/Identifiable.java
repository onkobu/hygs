package de.oftik.hygs.contract;

/**
 * Basic contract for an entity.
 * 
 * @author onkobu
 *
 */
public interface Identifiable {
	/**
	 * 
	 * @return Unique id within the source.
	 */
	long getId();

	/**
	 * 
	 * @return Source to make it distinct from other identifiables of other sources.
	 */
	EntitySource getSource();
}
