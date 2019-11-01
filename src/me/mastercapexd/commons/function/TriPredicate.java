package me.mastercapexd.commons.function;

import java.util.Objects;

@FunctionalInterface
public interface TriPredicate<F, S, T> {

	boolean test(F first, S second, T third);
	
	default TriPredicate<F, S, T> and(TriPredicate<? super F, ? super S, ? super T> other) {
		Objects.requireNonNull(other);
		return (F first, S second, T third) -> test(first, second, third) && other.test(first, second, third);
	}
	
	default TriPredicate<F, S, T> negate() {
		return (F first, S second, T third) -> !test(first, second, third);
	}
	
	default TriPredicate<F, S, T> or(TriPredicate<? super F, ? super S, ? super T> other) {
		Objects.requireNonNull(other);
		return (F first, S second, T third) -> test(first, second, third) || other.test(first, second, third);
	}
}