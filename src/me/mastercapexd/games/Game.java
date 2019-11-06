package me.mastercapexd.games;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

import me.mastercapexd.commons.Identifiable;

public interface Game extends Identifiable<String>, ActionRepository {

	@Nonnull
	Plugin getPlugin();
	
	@Nonnull
	Collection<GameBox> getGameBoxes();
	
	@Nullable
	GameBox getGameBox(@Nonnull String id);
	
	void addGameBox(@Nonnull GameBox box);
	
	void removeGameBox(@Nonnull String id);
	
	void enable();
	
	void disable();
	
	boolean isEnabled();
	
	@Nonnull
	default Collection<GamePlayer> getTotalPlayers() {
		Collection<GamePlayer> players = Sets.newHashSet();
		for (GameBox box : getGameBoxes())
			players.addAll(box.getPlayers());
		return players;
	}
	
	@Nonnull
	default Optional<GameAction> getGameActionOptional(String id) {
		return Optional.ofNullable(getGameAction(id));
	}
	
	default void removeGameBox(GameBox box) {
		removeGameBox(box.getIdentifier());
	}
	
	@Nonnull
	default Optional<GameBox> getGameBoxOptional(String id) {
		return Optional.ofNullable(getGameBox(id));
	}
}