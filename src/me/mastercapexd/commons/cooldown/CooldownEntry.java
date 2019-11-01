package me.mastercapexd.commons.cooldown;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Identifiable;

public class CooldownEntry<O> implements Identifiable<String>, Cloneable {

	private final String id;
	private final O owner;
	private long time;
	
	protected CooldownEntry(@Nonnull String id, @Nonnull O owner) {
		this.id = id;
		this.owner = owner;
	}
	
	@Override
	public String getIdentifier() {
		return id;
	}
	
	public O getOwner() {
		return owner;
	}
	
	public boolean isPassed() {
		if (time == -1)
			return true;
		
		return this.getMillisPassed() >= time;
	}
	
	public long getMillisPassed() {
		return (System.currentTimeMillis() - time);
	}
	
	public void reset() {
		this.time = System.currentTimeMillis();
	}
	
	public void add(long millis) {
		time+=millis;
	}
	
	public void subtract(long millis) {
		time-=millis;
	}
	
	public void set(long millis) {
		this.time = millis;
	}
	
	public void remove() {
		this.time = -1;
	}
	
	public String getEntry() {
		return id + ":" + time;
	}
	
	@Override
	public String toString() {
		return "CooldownEntry {id: " + id + ", owner: " + owner.toString() + ", time: " + time + "}";
	}
	
	@Override
	public CooldownEntry<O> clone() {
		try {
			@SuppressWarnings("unchecked")
			CooldownEntry<O> entry = (CooldownEntry<O>) super.clone();
			return entry;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
}