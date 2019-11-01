package me.mastercapexd.commons;

import javax.annotation.Nonnull;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import me.mastercapexd.commons.events.SingleEventSubscriptionBuilder;

public final class Events {

	@Nonnull
	public static <E extends Event> SingleEventSubscriptionBuilder<E> subscribe(@Nonnull Class<E> eventClass) {
		return new SingleEventSubscriptionBuilder<>(eventClass);
	}
	
	@Nonnull
	public static <E extends Event> SingleEventSubscriptionBuilder<E> subscribe(@Nonnull Class<E> eventClass, EventPriority priority) {
		return subscribe(eventClass).withPriority(priority);
	}
	
	private Events() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}