package me.mastercapexd.games;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

public interface GamePlayer extends GameEntity<Player> {

	void join(@Nonnull GameBox box);
	
	void quit();
	
	void join(@Nonnull Team team);
	
	void leave();
	
	default boolean isPlaying() {
		return getGameBox() != null;
	}
}