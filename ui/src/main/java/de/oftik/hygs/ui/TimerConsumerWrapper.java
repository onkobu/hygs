package de.oftik.hygs.ui;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.oftik.kehys.kersantti.query.QueryStatistics;

public final class TimerConsumerWrapper<T> implements Consumer<T>, QueryStatistics {
	private Instant started;
	private Instant stopped;
	private long durationMin = Long.MAX_VALUE;
	private long durationMax;
	private final Consumer<T> inner;
	private final Supplier<Instant> instantSupplier;
	private int rowCount;

	private TimerConsumerWrapper(Consumer<T> inner) {
		this(inner, Instant::now);
	}

	TimerConsumerWrapper(Consumer<T> inner, Supplier<Instant> instantSupplier) {
		this.instantSupplier = instantSupplier;
		this.inner = inner;
	}

	public static <T> TimerConsumerWrapper<T> wrap(Consumer<T> outer) {
		return new TimerConsumerWrapper<T>(outer);
	}

	@Override
	public void accept(T t) {
		if (started == null) {
			started = instantSupplier.get();
		}
		final long milliesStart = instantSupplier.get().toEpochMilli();
		inner.accept(t);
		rowCount++;
		final long milliesDiff = instantSupplier.get().toEpochMilli() - milliesStart;
		if (milliesDiff < durationMin) {
			durationMin = milliesDiff;
		}
		if (milliesDiff > durationMax) {
			durationMax = milliesDiff;
		}
	}

	public void start() {
		if (started == null) {
			started = instantSupplier.get();
		} else {
			throw new IllegalStateException("Cannot restart already started timer, even if stopped");
		}
	}

	public void stop() {
		if (stopped == null) {
			stopped = instantSupplier.get();
		} else {
			throw new IllegalStateException("Cannot stop already stopped timer");
		}
	}

	@Override
	public Instant getStarted() {
		return started;
	}

	@Override
	public Instant getStopped() {
		return stopped;
	}

	@Override
	public long getTotalMillies() {
		return started.until(stopped, ChronoUnit.MILLIS);
	}

	@Override
	public long getDurationMax() {
		return durationMax;
	}

	@Override
	public long getDurationMin() {
		return durationMin;
	}

	@Override
	public long getRowCount() {
		return rowCount;
	}
}
