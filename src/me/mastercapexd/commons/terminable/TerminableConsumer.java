package me.mastercapexd.commons.terminable;

import javax.annotation.Nonnull;

public interface TerminableConsumer {

	<T extends AutoCloseable> T bind(@Nonnull T terminable);
}