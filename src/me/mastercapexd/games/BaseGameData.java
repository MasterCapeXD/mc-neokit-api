package me.mastercapexd.games;

import java.util.Map;

import com.google.common.collect.Maps;

public class BaseGameData implements GameData {

	private final Map<String, Object> dataMap = Maps.newHashMap();
	
	@Override
	public boolean hasObjectValue(String key) {
		return dataMap.containsKey(key);
	}
	
	@Override
	public Object getObjectValue(String key) {
		return dataMap.get(key);
	}
	
	@Override
	public void writeObjectValue(String key, Object value) {
		dataMap.put(key, value);
	}
	
	@Override
	public void remove(String key) {
		dataMap.remove(key);
	}
	
	@Override
	public void clear() {
		dataMap.clear();
	}
}