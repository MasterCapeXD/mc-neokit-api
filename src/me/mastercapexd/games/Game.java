package me.mastercapexd.games;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

import me.mastercapexd.commons.Identifiable;

public interface Game<T extends Team> extends Identifiable<String> {

	@Nonnull
	Plugin getPlugin();
	
	@Nonnull
	Collection<GameBox<T>> getGameBoxes();
	
	@Nullable
	GameBox<T> getGameBox(@Nonnull String id);
	
	void addGameBox(@Nonnull GameBox<T> box);
	
	void removeGameBox(@Nonnull String id);
	
	void enable();
	
	void disable();
	
	boolean isEnabled();
	
	@Nonnull
	default Collection<GamePlayer> getTotalPlayers() {
		Collection<GamePlayer> players = Sets.newHashSet();
		for (GameBox<T> box : getGameBoxes())
			players.addAll(box.getPlayers());
		return players;
	}
	
	default void removeGameBox(GameBox<T> box) {
		removeGameBox(box.getIdentifier());
	}
	
	@Nonnull
	default Optional<GameBox<T>> getGameBoxOptional(String id) {
		return Optional.ofNullable(getGameBox(id));
	}
}