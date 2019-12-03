package me.mastercapexd.counters;

public class SimpleCounter implements Counter {

	private final long startTime, endTime;
	private long currentTime;
	private boolean paused = true;
	
	public SimpleCounter(long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public long getCurrentTime() {
		return currentTime;
	}

	@Override
	public long getEndTime() {
		return endTime;
	}

	@Override
	public void setTime(long time) {
		boolean increment = startTime < endTime;
		if (increment && time > endTime) {
			setPaused(true);
			return;
		} else if (!increment && time < startTime) {
			setPaused(true);
			return;
		}
		
		this.currentTime = time;
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}
}