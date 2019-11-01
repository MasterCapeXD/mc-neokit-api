package me.mastercapexd.commons.function;

public interface Terminable extends AutoCloseable {

	@Override
	default void close() throws Exception {
		terminate();
	}
	
	default boolean isClosed() {
		return false;
	}
	
	default Exception terminateSilently() {
		try {
			terminate();
			return null;
		} catch (Exception exception) {
			return exception;
		}
	}
	
	void terminate() throws Exception;
}