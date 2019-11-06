package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class BaseGameBox implements GameBox {

	private final String id;
	private final Map<Entity, GameEntity<?>> entities = Maps.newHashMap();
	private final Map<String, Team> teams;
	private final Map<String, GameAction> actions;
	
	public BaseGameBox(@Nonnull String id, @Nonnull Collection<Team> teams, @Nonnull Map<String, GameAction> actions) {
		this.id = id;
		com.google.common.collect.ImmutableMap.Builder<String, Team> builder = ImmutableMap .<String, Team> builder();
		for (Team team : teams)
			builder.put(team.getIdentifier(), team);
		this.teams = builder.build();
		this.actions = actions;
	}
	
	@Nonnull
	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Nonnull
	@Override
	public Collection<GameEntity<?>> getEntities() {
		return ImmutableSet.copyOf(entities.values());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> GameEntity<T> getEntityNullable(T entity) {
		return (GameEntity<T>) entities.get(entity);
	}
	
	@Nonnull
	@Override
	public Collection<Team> getRegisteredTeams() {
		return ImmutableSet.copyOf(teams.values());
	}
	
	@Nullable
	@Override
	public Team getTeam(String id) {
		return teams.get(id);
	}
	
	@Nullable
	@Override
	public GameAction getGameAction(String id) {
		return actions.get(id);
	}
	
	public void addEntity(GameEntity<?> entity) {
		entities.put(entity.getSource(), entity);
	}
	
	public void removeEntity(GameEntity<?> entity) {
		entities.remove(entity.getSource());
	}
	
	public static class Builder implements org.apache.commons.lang3.builder.Builder<GameBox> {
		
		protected final String id;
		protected final Map<String, GameAction> actions = Maps.newHashMap();
		protected final Collection<Team> teams = Sets.newHashSet();
		
		public Builder(@Nonnull String id) {
			this.id = id;
		}
		
		@Nonnull
		public Builder registerGameAction(@Nonnull GameAction action) {
			actions.put(action.getIdentifier(), action);
			return this;
		}
		
		@Nonnull
		public Builder registerTeam(@Nonnull Team team) {
			teams.add(team);
			return this;
		}
		
		@Nonnull
		@Override
		public GameBox build() {
			return new BaseGameBox(id, teams, actions);
		}
	}
}