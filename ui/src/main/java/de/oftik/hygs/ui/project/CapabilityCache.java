package de.oftik.hygs.ui.project;

import java.util.function.Consumer;

import de.oftik.hygs.contract.CacheListener;
import de.oftik.hygs.query.cap.Capability;

public interface CapabilityCache {
	Capability getCapabilityById(long id);

	void consumeAllCapabilities(Consumer<Capability> consumer);

	void addCacheListener(CacheListener listener);

	void removeCacheListener(CacheListener listener);
}
