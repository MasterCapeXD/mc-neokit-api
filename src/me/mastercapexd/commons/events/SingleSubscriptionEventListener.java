package me.mastercapexd.commons.events;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

public class SingleSubscriptionEventListener<T extends Event> implements SingleEventSubscription<T>, EventExecutor, Listener {

	private final Class<T> eventClass;
	private final EventPriority priority;
	private final boolean ignoreCancelled;
	
	private final Predicate<T>[] filters;
	private final BiPredicate<SingleEventSubscription<T>, T>[] preExpiryTests, postFilterExpiryTests, postHandleExpiryTests;
	private final BiConsumer<SingleEventSubscription<T>, ? super T>[] handlers;
	
	private final BiConsumer<? super T, Throwable> exceptionConsumer =
			(event, exception) -> {
				Bukkit.getLogger().severe("[Event Subscription] Exception thrown whilst handling event: " + event.getClass().getName());
				exception.printStackTrace();
			};
	
	private final AtomicInteger callCount = new AtomicInteger(0);
	private final AtomicBoolean active = new AtomicBoolean(true);
	private final long millis;
			
	@SuppressWarnings("unchecked")
	public SingleSubscriptionEventListener(@Nonnull SingleEventSubscriptionBuilder<T> subscriptionBuilder, @Nonnull List<BiConsumer<SingleEventSubscription<T>, ? super T>> handlers) {
		this.eventClass = subscriptionBuilder.getEvent();
		this.priority = subscriptionBuilder.getPriority();
		this.ignoreCancelled = subscriptionBuilder.ignoreCancelled();
		
		Collection<BiPredicate<SingleEventSubscription<T>, T>> pre = Sets.newHashSet(), postFilter = Sets.newHashSet(), postHandle = Sets.newHashSet();
		for (ExpiryTestStage stage : subscriptionBuilder.getPredicates().keySet()) {
			
			if (stage == ExpiryTestStage.PRE) {
				pre.addAll(subscriptionBuilder.getPredicates().get(stage));
				continue;
			} else if (stage == ExpiryTestStage.POST_FILTER) {
				postFilter.addAll(subscriptionBuilder.getPredicates().get(stage));
				continue;
			} else if (stage == ExpiryTestStage.POST_HANDLE) {
				postHandle.addAll(subscriptionBuilder.getPredicates().get(stage));
				continue;
			}
		}
		
		this.preExpiryTests = pre.toArray(new BiPredicate[pre.size()]);
		this.postFilterExpiryTests = postFilter.toArray(new BiPredicate[pre.size()]);
		this.postHandleExpiryTests = postHandle.toArray(new BiPredicate[pre.size()]);
		this.filters = subscriptionBuilder.filters().toArray(new Predicate[subscriptionBuilder.filters().size()]);
		this.handlers = handlers.toArray(new BiConsumer[handlers.size()]);
		this.millis = System.currentTimeMillis();
	}
	
	public void register(Plugin plugin) {
		Bukkit.getServer().getPluginManager().registerEvent(eventClass, this, priority, this, plugin, ignoreCancelled);
	}
	
	@Override
	public void execute(Listener listener, Event event) throws EventException {
		if (event.getClass() != getEventClass())
			return;
		
		if (this.expired()) {
			event.getHandlers().unregister(listener);
			return;
		}
		
		T eventInstance = this.eventClass.cast(event);
		
		for (BiPredicate<SingleEventSubscription<T>, T> test : preExpiryTests) {
			if (!test.test(this, eventInstance))
				continue;
			event.getHandlers().unregister(listener);
			this.active.set(false);
			return;
		}
		
		try {
			for (Predicate<T> filter : this.filters)
				if (!filter.test(eventInstance))
					return;
			
			for (BiPredicate<SingleEventSubscription<T>, T> test : postFilterExpiryTests)
				if (test.test(this, eventInstance)) {
					event.getHandlers().unregister(listener);
					this.active.set(false);
					return;
				}
			
			for (BiConsumer<SingleEventSubscription<T>, ? super T> handler : this.handlers)
				handler.accept(this, eventInstance);
			
			this.callCount.incrementAndGet();
		} catch (Throwable throwable) {
			this.exceptionConsumer.accept(eventInstance, throwable);
		}
		
		for (BiPredicate<SingleEventSubscription<T>, T> test : postHandleExpiryTests) {
			if (test.test(this, eventInstance)) {
				event.getHandlers().unregister(listener);
				expire();
				return;
			}
		}
	}
	
	@Nonnull
	@Override
	public Class<T> getEventClass() {
		return eventClass;
	}
	
	@Override
	public boolean expired() {
		return !active.get();
	}
	
	@Override
	public int calls() {
		return callCount.get();
	}
	
	@Override
	public void expire() {
		this.active.set(false);
		unregisterListener(this.eventClass, this);
	}
	
	@Override
	public long subscriptionTimeMillis() {
		return millis;
	}
	
	private static void unregisterListener(Class<? extends Event> eventClass, Listener listener) {
		try {
			Method getHandlerListMethod = eventClass.getMethod("getHandlerList");
			HandlerList handlerList = (HandlerList) getHandlerListMethod.invoke(null);
			handlerList.unregister(listener);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}