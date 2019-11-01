package me.mastercapexd.commons.events;

import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class SingleEventSubscriptionHandlerList<E extends Event> implements SingleEventHandlerList<E> {

	private final SingleEventSubscriptionBuilder<E> builder;
	private final List<BiConsumer<SingleEventSubscription<E>, ? super E>> handlers = Lists.newArrayListWithCapacity(0);
	
	public SingleEventSubscriptionHandlerList(@Nonnull SingleEventSubscriptionBuilder<E> builder) {
		this.builder = builder;
	}
	
	@Nonnull
	@Override
	public SubscriptionHandlerList<SingleEventSubscription<E>, E> addHandler(
			BiConsumer<SingleEventSubscription<E>, ? super E> consumer) {
		handlers.add(consumer);
		return null;
	}
	
	@Nonnull
	@Override
	public SingleEventSubscription<E> register(Plugin plugin) {
		Preconditions.checkState(!this.handlers.isEmpty(), "No handlers have been registered!");
		
		SingleSubscriptionEventListener<E> listener = new SingleSubscriptionEventListener<>(builder, handlers);
		listener.register(plugin);
		return listener;
	}
}