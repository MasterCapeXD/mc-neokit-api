package me.mastercapexd.commons;

import javax.annotation.Nonnull;

public interface Identifiable<T> {

	@Nonnull
	T getIdentifier();
}