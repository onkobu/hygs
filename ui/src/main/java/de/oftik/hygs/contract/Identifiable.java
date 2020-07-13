package de.oftik.hygs.contract;

/**
 * Basic contract for an entity.
 *
 * @author onkobu
 *
 */
public interface Identifiable extends de.oftik.keyhs.kersantti.Identifiable {
	/**
	 *
	 * @return Source to make it distinct from other identifiables of other sources.
	 */
	EntitySource getSource();

	default void createId() {
	}
}
