package me.mastercapexd.counters;

import javax.annotation.Nonnull;

public class CounterDecorator<T extends Counter> implements ProxyCounter<T> {

	private final T counter;
	
	public CounterDecorator(@Nonnull T counter) {
		this.counter = counter;
	}
	
	@Nonnull
	@Override
	public T getCounter() {
		return counter;
	}
}