package de.oftik.hygs.contract;

import de.oftik.kehys.kersantti.Table;

/**
 * Basic contract for an entity.
 *
 * @author onkobu
 *
 */
public interface Identifiable<I extends de.oftik.kehys.kersantti.Identifiable, T extends Table<I>> {
	/**
	 *
	 * @return Source to make it distinct from other identifiables of other sources.
	 */
	EntitySource<I, T> getSource();

	String getId();

	I unwrap();
}
