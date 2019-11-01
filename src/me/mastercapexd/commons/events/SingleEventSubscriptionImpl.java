package me.mastercapexd.commons.events;

import java.util.Collection;
import java.util.function.BiPredicate;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;

import com.google.common.collect.ImmutableSet;

import me.mastercapexd.commons.Delegates;

public class SingleEventSubscriptionImpl<T extends Event> extends AbstractSubscription implements SingleEventSubscription<T>{

	private final Class<T> event;
	private final Collection<BiPredicate<SingleEventSubscription<T>, T>> expirationPredicates;
	private boolean expiredForcibly;
	
	public SingleEventSubscriptionImpl(@Nonnull Class<T> event, @Nonnull Collection<BiPredicate<SingleEventSubscription<T>, T>> expirationPredicates) {
		this.event = event;
		this.expirationPredicates = ImmutableSet.copyOf(expirationPredicates);
	}
	
	@Nonnull
	@Override
	public Class<T> getEventClass() {
		return event;
	}
	
	@Override
	public void expire() {
		this.expiredForcibly = true;
	}
	
	@Override
	public boolean expired() {
		if (expiredForcibly)
			return true;
		
		return expirationPredicates.stream().allMatch(predicate -> Delegates.biPredicateToPredicateFirst(predicate).test(this));
	}
}