package me.mastercapexd.counters;

public interface Counter {

	long getStartTime();
	
	long getCurrentTime();
	
	long getEndTime();
	
	void setTime(long time);
	
	void setPaused(boolean paused);
	
	boolean isPaused();
	
	default void resetTime() {
		setTime(getStartTime());
	}
	
	default void addTime(int time) {
		setTime(getCurrentTime() + time);
	}
	
	default void subtractTime(int time) {
		setTime(getCurrentTime() - time);
	}
}