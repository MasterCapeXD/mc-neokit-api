package me.mastercapexd.commons.events;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public interface SubscriptionHandlerList<S extends SingleEventSubscription<E>, E extends Event> {

	@Nonnull
	SubscriptionHandlerList<S, E> addHandler(@Nonnull Consumer<? super E> consumer);
	
	@Nonnull
	SubscriptionHandlerList<S, E> addHandler(@Nonnull BiConsumer<S, ? super E> consumer);
	
	@Nonnull
	S register(Plugin plugin);
}