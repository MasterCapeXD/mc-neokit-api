package me.mastercapexd.commons.events;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;

public interface SingleEventSubscription<T extends Event> extends Subscription {

	@Nonnull
	Class<T> getEventClass();
}