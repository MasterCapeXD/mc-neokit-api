package me.mastercapexd.games;

public interface GameData {

	boolean hasObjectValue(String key);
	
	Object getObjectValue(String key);
	
	void writeObjectValue(String key, Object value);
	
	default boolean hasStringValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof String;
	}
	
	default String getStringValue(String key) {
		return (String) getObjectValue(key);
	}
	
	default void writeStringValue(String key, String value) {
		writeObjectValue(key, value);
	}
	
	default boolean hasByteValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof Byte;
	}
	
	default byte getByteValue(String key) {
		return (Byte) getObjectValue(key);
	}
	
	default void writeByteValue(String key, byte value) {
		writeObjectValue(key, value);
	}
	
	default boolean hasShortValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof Short;
	}
	
	default short getShortValue(String key) {
		return (Short) getObjectValue(key);
	}
	
	default void writeShortValue(String key, short value) {
		writeObjectValue(key, value);
	}
	
	default boolean hasIntValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof Integer;
	}
	
	default int getIntValue(String key) {
		return (Integer) getObjectValue(key);
	}
	
	default void writeIntValue(String key, int value) {
		writeObjectValue(key, value);
	}
	
	default boolean hasLongValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof Long;
	}
	
	default long getLongValue(String key) {
		return (Long) getObjectValue(key);
	}
	
	default void writeLongValue(String key, long value) {
		writeObjectValue(key, value);
	}
	
	default boolean hasBooleanValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof Boolean;
	}
	
	default boolean getBooleanValue(String key) {
		return (Boolean) getObjectValue(key);
	}
	
	default void writeBooleanValue(String key, boolean value) {
		writeObjectValue(key, value);
	}
	
	default boolean hasByteArrayValue(String key) {
		return hasObjectValue(key) && getObjectValue(key) instanceof byte[];
	}
	
	default byte[] getByteArrayValue(String key) {
		return (byte[]) getObjectValue(key);
	}
	
	default void writeByteArrayValue(String key, byte[] value) {
		writeObjectValue(key, value);
	}
	
	void remove(String key);
	
	void clear();
}