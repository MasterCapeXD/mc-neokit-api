package me.mastercapexd.commons.events;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class SingleEventSubscriptionBuilder<E extends Event> implements EventSubscriptionBuilder<SingleEventSubscriptionBuilder<E>, E, SingleEventSubscription<E>> {

	private final Class<E> event;
	private final Multimap<ExpiryTestStage, BiPredicate<SingleEventSubscription<E>, E>> predicates = HashMultimap.create();
	private final Collection<Predicate<E>> filters = Sets.newLinkedHashSet();
	
	private EventPriority priority = EventPriority.NORMAL;
	private Consumer<E> handler = event -> {};
	private boolean ignoreCancelled, registered;
	
	public SingleEventSubscriptionBuilder(Class<E> eventClass) {
		this.event = eventClass;
	}
	
	@Nonnull
	@Override
	public SingleEventSubscriptionBuilder<E> expiresIf(
			@Nonnull BiPredicate<SingleEventSubscription<E>, E> predicate, ExpiryTestStage... stages) {
		for (ExpiryTestStage stage : stages)
			predicates.put(stage, predicate);
		return this;
	}
	
	@Nonnull
	@Override
	public SingleEventSubscriptionBuilder<E> withPriority(@Nonnull EventPriority priority) {
		this.priority = priority;
		return this;
	}
	
	@Override
	public SingleEventSubscriptionBuilder<E> ignoreCancelled(boolean ignore) {
		this.ignoreCancelled = ignore;
		return this;
	}
	
	@Nonnull
	@Override
	public SingleEventSubscriptionBuilder<E> filter(@Nonnull Predicate<E> predicate) {
		filters.add(predicate);
		return this;
	}
	
	@Nonnull
	@Override
	public SingleEventSubscriptionBuilder<E> handle(@Nonnull Consumer<E> consumer) {
		this.handler = consumer;
		return this;
	}
	
	@Nonnull
	public Multimap<ExpiryTestStage, BiPredicate<SingleEventSubscription<E>, E>> getPredicates() {
		return predicates;
	}
	
	@Nullable
	@Override
	public Class<E> getEvent() {
		return event;
	}
	
	@Nonnull
	@Override
	public EventPriority getPriority() {
		return priority;
	}
	
	@Override
	public boolean ignoreCancelled() {
		return ignoreCancelled;
	}
	
	@Nonnull
	@Override
	public Collection<Predicate<E>> filters() {
		return filters;
	}
	
	@Nonnull
	@Override
	public Consumer<E> getHandler() {
		return handler;
	}
	
	@Nonnull
	@Override
	public void register(Plugin plugin) {
		SingleEventHandlerList<E> handlerList = new SingleEventSubscriptionHandlerList<>(this);
		handlerList.addHandler(handler);
		handlerList.register(plugin);
		this.registered = true;
	}
	
	@Override
	public boolean registered() {
		return registered;
	}
}