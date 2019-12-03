package me.mastercapexd.counters;

import java.util.Collection;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

public class SimpleTickingCounter<T extends Counter> extends TickingCounter<T> {

	private final Collection<Consumer<? super T>> tickConsumers;
	
	@SafeVarargs
	public SimpleTickingCounter(@Nonnull T counter, @Nonnull Consumer<? super T>... tickConsumers) {
		super(counter);
		this.tickConsumers = Sets.newHashSet(tickConsumers);
	}
	
	@Override
	protected void onTick() {
		for (Consumer<? super T> consumer : tickConsumers)
			consumer.accept(getCounter());
	}
}