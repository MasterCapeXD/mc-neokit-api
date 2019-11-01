package me.mastercapexd.commons.placeholders;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

public interface Placeholder<T> extends BiFunction<T, String, String> {

	static <T> Placeholder<T> create(String holder, Function<T, String> function) {
		return new Placeholder<T>() {

			@Override
			public String getHolder() {
				return holder;
			}

			@Override
			public Function<T, String> getApplier() {
				return function;
			}
		};
	}
	
	String getHolder();
	
	Function<T, String> getApplier();
	
	default boolean available(String rawString) {
		return rawString.contains(getHolder());
	}
	
	@Override
	default String apply(T type, String rawString) {
		return rawString.replaceAll(Pattern.quote(getHolder()), getApplier().apply(type));
	}
	
	default String undo(T type, String string) {
		return string.replace(Pattern.quote(getApplier().apply(type)), getHolder());
	}
}