package me.mastercapexd.counters;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class SimplePlayerCounter<T extends PlayerCounter> extends SimpleCounter implements PlayerCounter {

	private final Collection<Player> players = Sets.newHashSet();
	
	public SimplePlayerCounter(long startTime, long endTime) {
		super(startTime, endTime);
	}
	
	@Nonnull
	@Override
	public Collection<Player> getPlayers() {
		return ImmutableSet.copyOf(players);
	}
	
	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	@Override
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	@Override
	public boolean isPaused() {
		return super.isPaused() || isEmpty();
	}
}