package me.mastercapexd.counters;

public interface ProxyCounter<T extends Counter> extends Counter {

	T getCounter();
	
	@Override
	default long getStartTime() {
		return getCounter().getStartTime();
	}
	
	@Override
	default long getCurrentTime() {
		return getCounter().getCurrentTime();
	}
	
	@Override
	default long getEndTime() {
		return getCounter().getEndTime();
	}
	
	@Override
	default void setTime(long time) {
		getCounter().setTime(time);
	}
	
	@Override
	default void setPaused(boolean paused) {
		getCounter().setPaused(paused);
	}
	
	@Override
	default boolean isPaused() {
		return getCounter().isPaused();
	}
}