package me.mastercapexd.games;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import me.mastercapexd.commons.Events;
import me.mastercapexd.commons.events.ExpiryTestStage;
import me.mastercapexd.games.event.GameEntityFightEvent;

public class BaseGame implements Game {

	private final String id;
	private final Plugin plugin;
	private final Map<String, GameBox> gameBoxMap = Maps.newHashMap();
	private final Map<String, GameAction> actions;
	
	private boolean active;
	
	public BaseGame(@Nonnull String id, @Nonnull Plugin plugin, @Nonnull Collection<GameAction> actions) {
		this.id = id;
		this.plugin = plugin;
		com.google.common.collect.ImmutableMap.Builder<String, GameAction> actionMapBuilder = ImmutableMap.<String, GameAction> builder();
		for (GameAction action : actions)
			actionMapBuilder.put(action.getIdentifier(), action);
		this.actions = actionMapBuilder.build();
	}
	
	@Nonnull
	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Nonnull
	@Override
	public Plugin getPlugin() {
		return plugin;
	}
	
	@Nonnull
	@Override
	public Collection<GameBox> getGameBoxes() {
		return ImmutableSet.copyOf(gameBoxMap.values());
	}
	
	@Override
	public void addGameBox(GameBox box) {
		gameBoxMap.put(box.getIdentifier(), box);
	}
	
	@Override
	public void removeGameBox(String id) {
		gameBoxMap.remove(id);
	}
	
	@Nullable
	@Override
	public GameBox getGameBox(String id) {
		return gameBoxMap.get(id);
	}
	
	@Nullable
	@Override
	public GameAction getGameAction(String id) {
		return actions.get(id);
	}
	
	@Override
	public void enable() {
		this.active = true;
		Events.subscribe(EntityDamageByEntityEvent.class, EventPriority.LOWEST)
		.filter(event -> {
			Collection<Entity> entities = Sets.newHashSet();
			for (GameBox box : gameBoxMap.values())
				for (GameEntity<?> entity : box.getEntities())
					entities.add((Entity) entity.getSource());
			return entities.contains(event.getDamager()) && entities.contains(event.getEntity());
		})
		.expiresIf((subscription, event) -> !active, ExpiryTestStage.PRE)
		.handle(event -> {
			GameBox result = null;
			GameEntity<?> attacker = null, gameEntity = null;
			for (GameBox box : gameBoxMap.values())
				for (GameEntity<?> entity : box.getEntities()) {
					if (entity.equals(event.getDamager()))
						attacker = entity;
					if (entity.equals(event.getEntity()))
						gameEntity = entity;
					
					if (attacker != null && gameEntity != null)
						if (box.equals(attacker.getGameBox()) && box.equals(gameEntity.getGameBox()))
							result = box;
						break;
				}
			
			if (result == null)
				return;
			
			GameEntityFightEvent fightEvent = new GameEntityFightEvent(result, attacker, gameEntity);
			Bukkit.getServer().getPluginManager().callEvent(fightEvent);
			event.setCancelled(fightEvent.isCancelled());
		})
		.register(plugin);
	}
	
	@Override
	public void disable() {
		this.active = false;
	}
	
	@Override
	public final boolean isEnabled() {
		return active;
	}
	
	public static class Builder implements org.apache.commons.lang3.builder.Builder<Game> {
		
		private final String id;
		private final Plugin plugin;
		private final List<GameAction> actions = Lists.newArrayList();
		
		public Builder(@Nonnull String id, @Nonnull Plugin plugin) {
			this.id = id;
			this.plugin = plugin;
		}
		
		@Nonnull
		public Builder registerGameAction(@Nonnull GameAction action) {
			actions.add(action);
			return this;
		}
		
		@Nonnull
		@Override
		public Game build() {
			return new BaseGame(id, plugin, actions);
		}
	}
}