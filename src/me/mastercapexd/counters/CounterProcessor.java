package me.mastercapexd.counters;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface CounterProcessor {

	@Nonnull
	Collection<? extends Counter> getCounters();
	
	void addCounter(@Nonnull Counter counter);
	
	void removeCounter(@Nonnull Counter counter);
	
	void clear();
	
	void setProcessing(boolean processing);
	
	boolean isProcessing();
}