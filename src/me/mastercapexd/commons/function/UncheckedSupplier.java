package me.mastercapexd.commons.function;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface UncheckedSupplier extends Supplier<Object> {

	static UncheckedSupplier wrap(@Nonnull Object object) {
		return () -> object;
	}
	
	@Nullable
	static UncheckedSupplier wrapNullable(Object object) {
		return () -> object;
	}
	
	@Nonnull
	default <T> T tryCast(Class<T> to) {
		Optional<T> result = tryCastOptional(to);
		if (result.isPresent())
			return result.get();
		throw new ClassCastException();
	}
	
	@SuppressWarnings("unchecked")
	default <T> Optional<T> tryCastOptional(Class<T> to) {
		Object obj = get();
		if (getOptional().isPresent() && obj.getClass().isAssignableFrom(to))
			return Optional.ofNullable((T) obj);
		return Optional.empty();
	}
	
	@Nonnull
	default Optional<Object> getOptional() {
		return Optional.ofNullable(get());
	}
	
	@Nonnull
	default String getAsString() {
		return tryCast(String.class);
	}
	
	default char getAsChar() {
		return tryCast(Character.class);
	}
	
	default long getAsLong() {
		return tryCast(Long.class);
	}
	
	default int getAsInt() {
		return tryCast(Integer.class);
	}
	
	default short getAsShort() {
		return tryCast(Short.class);
	}
	
	default byte getAsByte() {
		return tryCast(Byte.class);
	}
	
	default boolean getAsBoolean() {
		return tryCast(Boolean.class);
	}
}