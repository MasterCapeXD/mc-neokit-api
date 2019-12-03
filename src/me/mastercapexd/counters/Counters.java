package me.mastercapexd.counters;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class Counters {

	@Nonnull
	@SafeVarargs
	public static Counter newSimpleTickingCounter(long startTime, long endTime, @Nonnull Consumer<? super Counter>... tickConsumers) {
		return new SimpleTickingCounter<Counter>(new SimpleCounter(startTime, endTime), tickConsumers);
	}
	
	@Nonnull
	@SafeVarargs
	public static PlayerCounter newPlayerTickingCounter(long startTime, long endTime, @Nonnull BiConsumer<? super Player, ? super PlayerCounter>... ticksConsumers) {
		return new PlayerTickingCounter<PlayerCounter>(new SimplePlayerCounter<>(startTime, endTime), ticksConsumers);
	}
	
	@Nonnull
	public static CounterProcessor newSyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin) {
		return new SyncBukkitTaskCounterProcessor(plugin);
	}
	
	@Nonnull
	public static CounterProcessor newSyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin, long period) {
		return new SyncBukkitTaskCounterProcessor(plugin, period);
	}
	
	@Nonnull
	public static CounterProcessor newAsyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin) {
		return new AsyncBukkitTaskCounterProcessor(plugin);
	}
	
	@Nonnull
	public static CounterProcessor newAsyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin, long period) {
		return new AsyncBukkitTaskCounterProcessor(plugin, period);
	}
	
	private Counters() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}