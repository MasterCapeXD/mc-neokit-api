/*
 * This file is part of helper, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

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