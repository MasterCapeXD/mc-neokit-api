package me.mastercapexd.games.event.internal;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.mastercapexd.games.GameBox;

public class GameBoxEnableEvent extends Event {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

	private final GameBox<?> box;
	
	public GameBoxEnableEvent(@Nonnull GameBox<?> box) {
		this.box = box;
	}
	
	@Nonnull
	public GameBox<?> getGameBox() {
		return box;
	}
	
	@Nonnull
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
}