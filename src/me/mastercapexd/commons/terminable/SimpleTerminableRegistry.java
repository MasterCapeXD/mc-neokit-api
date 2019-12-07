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

package me.mastercapexd.commons.terminable;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import me.mastercapexd.commons.function.Terminable;

public class SimpleTerminableRegistry implements TerminableRegistry {

	private final Deque<AutoCloseable> closeables = new ConcurrentLinkedDeque<>();
	
	protected SimpleTerminableRegistry() {}
	
	@Override
	public TerminableRegistry with(@Nonnull AutoCloseable autoCloseable) {
		this.closeables.push(autoCloseable);
		return this;
	}
	
	@Override
	public void cleanup() {
		this.closeables.removeIf(autoCloseable -> {
			if (!(autoCloseable instanceof Terminable))
				return false;
			if (autoCloseable instanceof TerminableRegistry)
				((TerminableRegistry) autoCloseable).cleanup();
			return ((Terminable) autoCloseable).isClosed();
		});
	}

	@Override
	public void terminate() throws TerminableClosingException {
		Collection<Exception> caught = Lists.newArrayList();
		for (AutoCloseable autoCloseable; (autoCloseable = this.closeables.poll()) != null;) {
			try {
				autoCloseable.close();
			} catch (Exception exception) {
				caught.add(exception);
			}
		}
		
		if (!caught.isEmpty())
			throw new TerminableClosingException(caught);
	}
}