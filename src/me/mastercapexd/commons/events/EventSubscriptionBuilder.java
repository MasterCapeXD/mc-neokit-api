package me.mastercapexd.commons.events;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import me.mastercapexd.commons.Delegates;

public interface EventSubscriptionBuilder<B extends EventSubscriptionBuilder<B, E, S>, E extends Event, S extends Subscription> {

	@Nonnull
	B expiresIf(@Nonnull BiPredicate<S, E> predicate, ExpiryTestStage... stages);
	
	@Nonnull
	Class<E> getEvent();
	
	@Nonnull
	B withPriority(@Nonnull EventPriority priority);
	
	B ignoreCancelled(boolean ignore);
	
	@Nonnull
	EventPriority getPriority();
	
	boolean ignoreCancelled();
	
	@Nonnull
	B filter(@Nonnull Predicate<E> predicate);
	
	@Nonnull
	Collection<Predicate<E>> filters();
	
	@Nonnull
	B handle(@Nonnull Consumer<E> consumer);
	
	@Nonnull
	Consumer<E> getHandler();
	
	@Nonnull
	default B expiresIn(long time, @Nullable TimeUnit unit) {
		TimeUnit timeUnit = unit == null ? TimeUnit.MINUTES : unit;
		return expiresIf(Delegates.predicateToBiPredicateFirst(
				subscription -> System.currentTimeMillis() - subscription.subscriptionTimeMillis() > timeUnit.toMillis(time)),
				ExpiryTestStage.POST_HANDLE);
	}
	
	@Nonnull
	default B limit(int calls) {
		return expiresIf(Delegates.predicateToBiPredicateFirst(subscription -> subscription.calls() >= calls), ExpiryTestStage.POST_HANDLE);
	}
	
	void register(Plugin plugin);
	
	boolean registered();
}