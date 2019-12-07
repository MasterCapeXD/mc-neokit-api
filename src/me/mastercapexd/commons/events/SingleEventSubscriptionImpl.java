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