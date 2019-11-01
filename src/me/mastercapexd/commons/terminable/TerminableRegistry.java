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