package me.mastercapexd.counters;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public abstract class AbstractCounterProcessor implements CounterProcessor {

	protected final Collection<Counter> counters = Sets.newHashSet();
	
	protected AbstractCounterProcessor() {}
	
	@Nonnull
	@Override
	public Collection<? extends Counter> getCounters() {
		return ImmutableSet.copyOf(counters);
	}
	
	@Override
	public void addCounter(@Nonnull Counter counter) {
		counters.add(counter);
	}
	
	@Override
	public void removeCounter(@Nonnull Counter counter) {
		counters.remove(counter);
	}
	
	@Override
	public void clear() {
		counters.clear();
	}
	
	protected void handle() {
		for (Counter counter : getCounters()) {
			if (counter.isPaused())
				continue;
			
			if (counter.getStartTime() < counter.getEndTime())
				counter.addTime(1);
			else
				counter.subtractTime(1);
		}
	}
}