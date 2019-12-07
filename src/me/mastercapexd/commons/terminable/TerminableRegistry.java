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

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import me.mastercapexd.commons.function.Terminable;

public interface TerminableRegistry extends Terminable, TerminableConsumer {

	@Nonnull
	static TerminableRegistry create() {
		return new SimpleTerminableRegistry();
	}
	
	@Override
	void terminate() throws TerminableClosingException;
	
	@Override
	default TerminableClosingException terminateSilently() {
		try {
			terminate();
			return null;
		} catch (TerminableClosingException exception) {
			return exception;
		}
	}
	
	@Nonnull
	TerminableRegistry with(@Nonnull AutoCloseable autoCloseable);
	
	@Nonnull
	default TerminableRegistry withAll(Iterable<AutoCloseable> autoCloseables) {
		for (AutoCloseable autoCloseable : autoCloseables) {
			if (autoCloseable == null)
				continue;
			bind(autoCloseable);
		}
		return this;
	}
	
	@Nonnull
	default TerminableRegistry withAll(AutoCloseable... autoCloseables) {
		return withAll(Sets.newHashSet(autoCloseables));
	}
	
	@Nonnull
	@Override
	default <T extends AutoCloseable> T bind(@Nonnull T terminable) {
		with(terminable);	
		return terminable;
	}
	
	void cleanup();
}