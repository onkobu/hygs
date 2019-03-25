package de.oftik.hygs.query;

import java.time.Instant;

public interface QueryStatistics {
	Instant getStarted();

	Instant getStopped();

	long getRowCount();

	long getTotalMillies();

	long getDurationMax();

	long getDurationMin();
}
