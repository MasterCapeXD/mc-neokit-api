package me.mastercapexd.counters;

import javax.annotation.Nonnull;

public abstract class TickingCounter<T extends Counter> extends CounterDecorator<T> {

	public TickingCounter(@Nonnull T counter) {
		super(counter);
	}
	
	@Override
	public void setTime(long time) {
		onTick();
		super.setTime(time);
	}
	
	protected abstract void onTick();
}