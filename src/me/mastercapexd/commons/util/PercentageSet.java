package me.mastercapexd.commons.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface PercentageSet<T> extends Supplier<T> {

	void put(T type, int percentage);
	
	void putAll(Map<T, Integer> map);
	
	void addAll(Collection<T> collection);
	
	void remove(T type);
	
	void removeAll(Collection<T> collection);
	
	void clear();
	
	boolean contains(T type);
	
	boolean containsAll(Collection<T> collection);
	
	boolean isEmpty();
	
	int size();
}