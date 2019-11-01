package me.mastercapexd.commons.events;

import org.bukkit.event.Cancellable;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

@SuppressWarnings("unchecked")
public final class EventHandlers {

	private static final Consumer<? extends Cancellable> SET_CANCELLED = e -> e.setCancelled(true);
	private static final Consumer<? extends Cancellable> UNSET_CANCELLED = e -> e.setCancelled(false);
	
	@Nonnull
	public static <T extends Cancellable> Consumer<T> cancel() {
		return (Consumer<T>) SET_CANCELLED;
	}
	
	@Nonnull
	public static <T extends Cancellable> Consumer<T> uncancel() {
		return (Consumer<T>) UNSET_CANCELLED;
	}
	
	private EventHandlers() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}