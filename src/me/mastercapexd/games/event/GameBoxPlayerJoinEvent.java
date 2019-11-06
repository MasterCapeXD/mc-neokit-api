package me.mastercapexd.games.event;

import javax.annotation.Nonnull;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.mastercapexd.games.GameBox;
import me.mastercapexd.games.GamePlayer;

public class GameBoxPlayerJoinEvent extends Event implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
	
	private final GamePlayer player;
	private final GameBox box;
	private boolean cancelled;
	
	public GameBoxPlayerJoinEvent(@Nonnull GamePlayer player, @Nonnull GameBox box) {
		this.player = player;
		this.box = box;
	}
	
	@Nonnull
	public GamePlayer getPlayer() {
		return player;
	}
	
	@Nonnull
	public GameBox getGameBox() {
		return box;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Nonnull
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
}