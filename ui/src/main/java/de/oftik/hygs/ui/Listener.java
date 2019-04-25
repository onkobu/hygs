package de.oftik.hygs.ui;

/**
 * Some callback upon an event.
 * 
 * @author onkobu
 *
 * @param <E>
 */
public interface Listener<E extends Event> {
	void onEvent(E e);
}
