package de.oftik.hygs.contract;

/**
 * Basic contract for an entity.
 *
 * @author onkobu
 *
 */
public interface Identifiable<T extends Identifiable<T>> extends de.oftik.keyhs.kersantti.Identifiable {
	/**
	 *
	 * @return Source to make it distinct from other identifiables of other sources.
	 */
	EntitySource<T> getSource();

	default void createId() {
	}
}
