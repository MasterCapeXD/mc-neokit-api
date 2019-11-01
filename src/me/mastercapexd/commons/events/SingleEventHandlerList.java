package me.mastercapexd.commons.events;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;

import me.mastercapexd.commons.Delegates;

public interface SingleEventHandlerList<E extends Event> extends SubscriptionHandlerList<SingleEventSubscription<E>, E>{

	@Nonnull
	@Override
	default SubscriptionHandlerList<SingleEventSubscription<E>, E> addHandler(@Nonnull Consumer<? super E> consumer) {
		return this.addHandler(Delegates.consumerToBiConsumerSecond(consumer));
	}
}