package de.oftik.hygs.ui;

import java.util.HashSet;
import java.util.Set;

/**
 * Holds record of listeners for a specific event type. The contract with
 * {@link Listener} is based on the single method to be called and a single
 * event type. Thus a responsibility for a single task must be the consequence.
 * 
 * @author onkobu
 *
 * @param <E>
 * @param <L>
 */
public class Listenable<E extends Event, L extends Listener<E>> {
	private final Set<L> listeners = new HashSet<>();

	public void addListener(L l) {
		listeners.add(l);
	}

	public void removeListener(L l) {
		listeners.remove(l);
	}

	protected void publish(E evt) {
		listeners.forEach((l) -> l.onEvent(evt));
	}
}
