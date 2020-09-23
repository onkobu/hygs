package de.oftik.hygs.ui.project;

import java.util.function.Consumer;

import de.oftik.hygs.contract.CacheListener;

public interface CapabilityCache {
	CapabilityWithCategory getCapabilityById(String id);

	void consumeAllCapabilities(Consumer<CapabilityWithCategory> consumer);

	void addCacheListener(CacheListener listener);

	void removeCacheListener(CacheListener listener);
}
