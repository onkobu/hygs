package de.oftik.hygs.ui;

import java.time.Instant;

public interface QueryStatistics {
	Instant getStarted();

	Instant getStopped();

	long getRowCount();

	long getTotalMillies();

	long getDurationMax();

	long getDurationMin();
}
