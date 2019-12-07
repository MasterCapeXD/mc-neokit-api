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

import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class SingleEventSubscriptionHandlerList<E extends Event> implements SingleEventHandlerList<E> {

	private final SingleEventSubscriptionBuilder<E> builder;
	private final List<BiConsumer<SingleEventSubscription<E>, ? super E>> handlers = Lists.newArrayListWithCapacity(0);
	
	public SingleEventSubscriptionHandlerList(@Nonnull SingleEventSubscriptionBuilder<E> builder) {
		this.builder = builder;
	}
	
	@Nonnull
	@Override
	public SubscriptionHandlerList<SingleEventSubscription<E>, E> addHandler(
			BiConsumer<SingleEventSubscription<E>, ? super E> consumer) {
		handlers.add(consumer);
		return null;
	}
	
	@Nonnull
	@Override
	public SingleEventSubscription<E> register(Plugin plugin) {
		Preconditions.checkState(!this.handlers.isEmpty(), "No handlers have been registered!");
		
		SingleSubscriptionEventListener<E> listener = new SingleSubscriptionEventListener<>(builder, handlers);
		listener.register(plugin);
		return listener;
	}
}