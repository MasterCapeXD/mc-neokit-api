package me.mastercapexd.commons;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import me.mastercapexd.commons.function.Delegate;

public final class Delegates {

	public static <T> Consumer<T> runnableToConsumer(Runnable runnable) {
		return new RunnableToConsumer<>(runnable);
	}
	
	public static Supplier<Void> runnableToSupplier(Runnable runnable) {
		return new RunnableToSupplier<>(runnable);
	}
	
	public static <T> Supplier<T> callableToSupplier(Callable<T> callable) {
		return new CallableToSupplier<>(callable);
	}
	
	public static <T, U> BiConsumer<T, U> consumerToBiConsumerFirst(Consumer<T> consumer) {
		return new ConsumerToBiConsumerFirst<>(consumer);
	}
	
	public static <T, U> BiConsumer<T, U> consumerToBiConsumerSecond(Consumer<U> consumer) {
		return new ConsumerToBiConsumerSecond<>(consumer);
	}
	
	public static <T, U> BiPredicate<T, U> predicateToBiPredicateFirst(Predicate<T> predicate) {
		return new PredicateToBiPredicateFirst<>(predicate);
	}
	
	public static <T, U> BiPredicate<T, U> predicateToBiPredicateSecond(Predicate<U> predicate) {
		return new PredicateToBiPredicateSecond<>(predicate);
	}
	
	public static <T, U> Predicate<T> biPredicateToPredicateFirst(BiPredicate<T, U> predicate) {
		return new BiPredicateToPredicateFirst<>(predicate);
	}
	
	public static <T, U> Predicate<U> biPredicateToPredicateSecond(BiPredicate<T, U> predicate) {
		return new BiPredicateToPredicateSecond<>(predicate);
	}
	
	public static <T, U> Function<T, U> consumerToFunction(Consumer<T> consumer) {
		return new ConsumerToFunction<>(consumer);
	}
	
	public static <T, U> Function<T, U> runnableToFunction(Runnable runnable) {
		return new RunnableToFunction<>(runnable);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T, U> Function<T, U> supplierToFunction(Supplier<U> supplier) {
		return new SupplierToFunction(supplier);
	}
	
	private static final class RunnableToConsumer<T> implements Consumer<T>, Delegate<Runnable> {
		
		private final Runnable delegate;
		
		private RunnableToConsumer(Runnable delegate) {
			this.delegate = delegate;
		}
		
		@Override public Runnable getDelegate() { return this.delegate; }
		
		@Override
		public void accept(T t) {
			this.delegate.run();
		}
    }
	
	private static final class CallableToSupplier<T> implements Supplier<T>, Delegate<Callable<T>> {
		
		private final Callable<T> delegate;
		
		private CallableToSupplier(Callable<T> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Callable<T> getDelegate() { return this.delegate; }
		
		@Override
		public T get() {
			try {
				return this.delegate.call();
			} catch (RuntimeException | Error e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private static final class RunnableToSupplier<T> implements Supplier<T>, Delegate<Runnable> {
		
		private final Runnable delegate;
		
		private RunnableToSupplier(Runnable delegate) {
			this.delegate = delegate;
		}
		
		@Override public Runnable getDelegate() { return this.delegate; }
		
		@Override
		public T get() {
			this.delegate.run();
			return null;
		}
	}
	
	private static final class ConsumerToBiConsumerFirst<T, U> implements BiConsumer<T, U>, Delegate<Consumer<T>> {
		
		private final Consumer<T> delegate;
		
		private ConsumerToBiConsumerFirst(Consumer<T> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Consumer<T> getDelegate() { return this.delegate; }
		
		@Override
		public void accept(T t, U u) {
			this.delegate.accept(t);
		}
	}
	
	private static final class ConsumerToBiConsumerSecond<T, U> implements BiConsumer<T, U>, Delegate<Consumer<U>> {
		
		private final Consumer<U> delegate;
		
		private ConsumerToBiConsumerSecond(Consumer<U> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Consumer<U> getDelegate() { return this.delegate; }
		
		@Override
		public void accept(T t, U u) {
			this.delegate.accept(u);
		}
	}
	
	private static final class PredicateToBiPredicateFirst<T, U> implements BiPredicate<T, U>, Delegate<Predicate<T>> {
		
		private final Predicate<T> delegate;
		
		private PredicateToBiPredicateFirst(Predicate<T> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Predicate<T> getDelegate() { return this.delegate; }
		
		@Override
		public boolean test(T t, U u) {
			return this.delegate.test(t);
		}
	}
	
	private static final class PredicateToBiPredicateSecond<T, U> implements BiPredicate<T, U>, Delegate<Predicate<U>> {
		
		private final Predicate<U> delegate;
		
		private PredicateToBiPredicateSecond(Predicate<U> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Predicate<U> getDelegate() { return this.delegate; }
		
		@Override
		public boolean test(T t, U u) {
			return this.delegate.test(u);
		}
	}
	
	private static final class BiPredicateToPredicateFirst<T, U> implements Predicate<T>, Delegate<BiPredicate<T, ?>> {
		
		private final BiPredicate<T, ?> delegate;
		
		private BiPredicateToPredicateFirst(BiPredicate<T, ?> delegate) {
			this.delegate = delegate;
		}
		
		@Override public BiPredicate<T, ?> getDelegate() { return this.delegate; }
		
		@Override
		public boolean test(T t) {
			return this.delegate.test(t, null);
		}
	}
	
	private static final class BiPredicateToPredicateSecond<T, U> implements Predicate<U>, Delegate<BiPredicate<?, U>> {
		
		private final BiPredicate<?, U> delegate;
		
		private BiPredicateToPredicateSecond(BiPredicate<?, U> delegate) {
			this.delegate = delegate;
		}
		
		@Override public BiPredicate<?, U> getDelegate() { return this.delegate; }
		
		@Override
		public boolean test(U u) {
			return this.delegate.test(null, u);
		}
	}
	
	private static final class ConsumerToFunction<T, R> implements Function<T, R>, Delegate<Consumer<T>> {
		
		private final Consumer<T> delegate;
		
		private ConsumerToFunction(Consumer<T> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Consumer<T> getDelegate() { return this.delegate; }
		
		@Override
		public R apply(T t) {
			this.delegate.accept(t);
			return null;
		}
	}
	
	private static final class RunnableToFunction<T, R> implements Function<T, R>, Delegate<Runnable> {
		
		private final Runnable delegate;
		
		private RunnableToFunction(Runnable delegate) {
			this.delegate = delegate;
		}
		
		@Override public Runnable getDelegate() { return this.delegate; }
		
		@Override
		public R apply(T t) {
			this.delegate.run();
			return null;
		}
	}
	
	private static final class SupplierToFunction<T, R> implements Function<T, R>, Delegate<Supplier<R>> {
		
		private final Supplier<R> delegate;
		
		private SupplierToFunction(Supplier<R> delegate) {
			this.delegate = delegate;
		}
		
		@Override public Supplier<R> getDelegate() { return this.delegate; }
		
		@Override
		public R apply(T t) {
			return getDelegate().get();
		}
	}
	
	private Delegates() { throw new UnsupportedOperationException("This class cannot be instantiated"); }
}
