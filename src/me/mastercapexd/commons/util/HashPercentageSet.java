package me.mastercapexd.commons.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class HashPercentageSet<T> implements PercentageSet<T>, Iterable<T> {

	private final Map<T, Integer> percentages = Maps.newHashMap();
	
	@Override
	public T get() {
		final double random = Math.random();
		T result = null;
		double current = 0.0;
		@SuppressWarnings("unchecked")
		Entry<T, Byte>[] entries = prepare(percentages.entrySet().toArray(new Entry[percentages.size()]));
		
		for (int i = 0; i < entries.length; i++) {
			Entry<T, Byte> entry = entries[i];
			result = entry.getKey();
			current+=(double) entry.getValue() / 100;
			
			double next = 0.0;
			if (i + 1 < entries.length)
				next = (double) entries[i + 1].getValue() / 100;
			
			if (current >= random && next < current)
				break;
		}
		return result;
	}
	
	@Override
	public void put(T type, int percentage) {
		percentages.put(type, percentage);
	}
	
	@Override
	public void putAll(Map<T, Integer> map) {
		percentages.putAll(map);
	}
	
	@Override
	public void addAll(Collection<T> collection) {
		int percentage = 100 / collection.size();
		for (T type : collection)
			put(type, percentage);
	}
	
	@Override
	public void remove(T type) {
		percentages.remove(type);
	}
	
	@Override
	public void removeAll(Collection<T> collection) {
		for (T type : collection)
			remove(type);
	}
	
	@Override
	public void clear() {
		percentages.clear();
	}
	
	@Override
	public boolean contains(T type) {
		return percentages.containsKey(type);
	}
	
	@Override
	public boolean containsAll(Collection<T> collection) {
		return percentages.keySet().containsAll(collection);
	}
	
	@Override
	public boolean isEmpty() {
		return percentages.isEmpty();
	}
	
	@Override
	public int size() {
		return percentages.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		return percentages.keySet().iterator();
	}
	
	private Entry<T, Byte>[] prepare(Entry<T, Integer>[] entries) {
		@SuppressWarnings("unchecked")
		Entry<T, Byte>[] preparedPercents = new Entry[entries.length];
		int summary = summary(entries);
		float coefficient = (float) summary / 100;
		for (int i = 0; i < entries.length; i++)
			preparedPercents[i] = Maps.immutableEntry(entries[i].getKey(), ((byte) (entries[i].getValue() / coefficient)));
		return preparedPercents;
	}
	
	private int summary(Entry<T, Integer>[] entries) {
		int summary = 0;
		for (Entry<?, Integer> pair : entries)
			summary+=pair.getValue();
		return summary;
	}
}