package me.mastercapexd.games;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.mastercapexd.games.event.GameBoxPlayerJoinEvent;
import me.mastercapexd.games.event.GameBoxPlayerQuitEvent;
import me.mastercapexd.games.event.PlayerTeamJoinEvent;
import me.mastercapexd.games.event.PlayerTeamLeaveEvent;

public class BasePlayer extends BaseEntity<Player> implements GamePlayer {

	public BasePlayer(@Nonnull String id, @Nonnull Player entity) {
		super(id, entity);
	}
	
	@Override
	public void join(@Nonnull GameBox box) {
		GameBoxPlayerJoinEvent event = new GameBoxPlayerJoinEvent(this, box);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		
		if (this.getGameBox() != null)
			quit();
		this.box = box;
		((BaseGameBox) this.box).addEntity(this);
	}
	
	@Override
	public void quit() {
		if (this.getGameBox() == null)
			return;
		
		GameBoxPlayerQuitEvent event = new GameBoxPlayerQuitEvent(this, this.getGameBox());
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		
		leave();
		((BaseGameBox) this.box).removeEntity(this);
		this.box = null;
	}
	
	@Override
	public void join(@Nonnull Team team) {
		if (this.getGameBox() == null || this.getGameBox().getTeam(team.getIdentifier()) == null)
			return;
		
		PlayerTeamJoinEvent event = new PlayerTeamJoinEvent(this, team);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		
		if (this.getTeam() != null)
			leave();
		this.team = team;
		((BaseTeam) this.team).addEntity(this);
	}
	
	@Override
	public void leave() {
		if (this.getTeam() == null)
			return;
		
		PlayerTeamLeaveEvent event = new PlayerTeamLeaveEvent(this, this.getTeam());
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		
		((BaseTeam) this.team).removeEntity(this);
		this.team = null;
	}
}