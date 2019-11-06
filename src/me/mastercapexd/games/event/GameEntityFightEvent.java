package me.mastercapexd.games.event;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.mastercapexd.games.GameBox;
import me.mastercapexd.games.GameEntity;

public class GameEntityFightEvent extends Event implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
	
	private final GameBox box;
	private final GameEntity<? extends Entity> attacker, entity;
	
	private boolean cancelled;
	
	public GameEntityFightEvent(@Nonnull GameBox box, @Nonnull GameEntity<? extends Entity> attacker, @Nonnull GameEntity<? extends Entity> entity) {
		this.box = box;
		this.attacker = attacker;
		this.entity = entity;
	}
	
	@Nonnull
	public GameBox getBox() {
		return box;
	}
	
	@Nonnull
	public GameEntity<? extends Entity> getAttacker() {
		return attacker;
	}
	
	@Nonnull
	public GameEntity<? extends Entity> getEntity() {
		return entity;
	}
	
	@Nonnull
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}