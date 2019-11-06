package me.mastercapexd.games.event;

import javax.annotation.Nonnull;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.mastercapexd.games.GameBox;
import me.mastercapexd.games.GamePlayer;
import me.mastercapexd.games.Team;

public class PlayerTeamJoinEvent extends Event implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
	
	private final GamePlayer player;
	private final Team team;
	private boolean cancelled;
	
	public PlayerTeamJoinEvent(@Nonnull GamePlayer player, @Nonnull Team team) {
		this.player = player;
		this.team = team;
	}
	
	@Nonnull
	public GamePlayer getPlayer() {
		return player;
	}
	
	@Nonnull
	public Team getTeam() {
		return team;
	}
	
	@Nonnull
	public GameBox getGameBox() {
		return player.getGameBox();
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