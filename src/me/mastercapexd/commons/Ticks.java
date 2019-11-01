package me.mastercapexd.commons;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

public final class Ticks {

	public static final int TICKS_PER_SECOND = 20;
	
	public static final int MILLISECONDS_PER_SECOND = 1000;
	
	public static final int MILLISECONDS_PER_TICK = MILLISECONDS_PER_SECOND / TICKS_PER_SECOND;
	
	public static long from(long duration, @Nonnull TimeUnit unit) {
		return unit.toMillis(duration) / MILLISECONDS_PER_TICK;
	}
	
	public static long to(long ticks, @Nonnull TimeUnit unit) {
		return unit.convert(ticks * MILLISECONDS_PER_TICK, TimeUnit.MILLISECONDS);
	}
	
	private Ticks() {
		throw new UnsupportedOperationException("This class cannot be instantiated");
	}
}