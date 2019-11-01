package me.mastercapexd.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<F, S, T, R> {

	R apply(F first, S second, T third);
	
	default <C> TriFunction<F, S, T, C> andThen(Function<? super R, ? extends C> after) {
		Objects.requireNonNull(after);
		return (first, second, third) -> {
			return after.apply(this.apply(first, second, third));
		};
	}
}