package me.mastercapexd.commons.cooldown;

import java.util.AbstractList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public class CooldownEntryList<O> extends AbstractList<String> {

	private final O owner;
	private final List<String> entries = Lists.newArrayList();
	
	public CooldownEntryList(@Nonnull O owner) {
		this.owner = owner;
	}
	
	public O getOwner() {
		return owner;
	}
	
	public void add(CooldownEntry<O> cooldownEntry) {
		entries.add(cooldownEntry.getEntry());
	}
	
	public void remove(CooldownEntry<O> cooldownEntry) {
		entries.remove(cooldownEntry.getEntry());
	}
	
	public String get(String id) {
		return entries.stream().filter(e -> e.split(":")[0].equals(id)).findFirst().get();
	}
	
	@Override
	public String get(int index) {
		return entries.get(index);
	}
	
	@Override
	public int size() {
		return entries.size();
	}
}