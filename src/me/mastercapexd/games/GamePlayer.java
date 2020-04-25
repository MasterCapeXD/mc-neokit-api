package me.mastercapexd.games;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

public interface GamePlayer extends GameEntity<Player> {

	<T extends Team> void join(@Nonnull GameBox<T> box);
	
	void quit();
	
	void join(@Nonnull Team team);
	
	void leave();
	
	default boolean isPlaying() {
		return getGameBox() != null;
	}
}