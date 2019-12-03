package me.mastercapexd.counters;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

public interface PlayerCounter extends Counter {

	@Nonnull
	Collection<Player> getPlayers();
	
	void addPlayer(@Nonnull Player player);
	
	void removePlayer(@Nonnull Player player);
	
	default boolean isEmpty() {
		return getPlayers().isEmpty();
	}
}