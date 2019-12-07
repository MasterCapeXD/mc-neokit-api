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
import java.util.List;

import com.google.common.collect.ImmutableList;

public class TerminableClosingException extends Exception {

	private static final long serialVersionUID = 4849871693685578333L;
	
	private final List<? extends Throwable> causes;
	
	public TerminableClosingException(Collection<? extends Throwable> causes) {
		super("Exception(s) occurred whilst closing: " + causes.toString());
		if (causes.isEmpty())
			throw new IllegalArgumentException("No causes");
		this.causes = ImmutableList.copyOf(causes);
    }
	
	public List<? extends Throwable> getCauses() {
		return this.causes;
	}
	
	public void printAllStackTraces() {
		this.printStackTrace();
		for (Throwable cause : this.causes)
			cause.printStackTrace();
	}
}