package me.mastercapexd.commons.function;

public interface Delegate<T> {

	static Object resolve(Object obj) {
		while (obj instanceof Delegate<?>) {
			Delegate<?> delegate = (Delegate<?>) obj;
			obj = delegate.getDelegate();
		}
		return obj;
	}
	
	T getDelegate();
}