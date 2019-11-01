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