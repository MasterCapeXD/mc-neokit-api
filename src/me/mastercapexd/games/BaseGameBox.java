package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public class BaseGameBox<T extends Team> implements GameBox<T> {

	private final String id;
	private final Map<Entity, GameEntity<?>> entities = Maps.newHashMap();
	private final Map<String, T> teams = Maps.newHashMap();
	private boolean enabled;
	private final GameData data;
	
	public BaseGameBox(@Nonnull String id, Collection<T> teams) {
		this.id = id;
		for (T team : teams)
			this.teams.put(team.getIdentifier(), team);
		this.data = new BaseGameData();
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
	public <E extends Entity> GameEntity<E> getEntityNullable(E entity) {
		return (GameEntity<E>) entities.get(entity);
	}
	
	@Nonnull
	@Override
	public Collection<T> getRegisteredTeams() {
		return ImmutableSet.copyOf(teams.values());
	}
	
	@Nullable
	@Override
	public T getTeam(String id) {
		return teams.get(id);
	}
	
	@Override
	public GameData getData() {
		return data;
	}
	
	public void addEntity(GameEntity<?> entity) {
		entities.put(entity.getSource(), entity);
	}
	
	public void removeEntity(GameEntity<?> entity) {
		entities.remove(entity.getSource());
	}
	
	@Override
	public void enable() {
		this.enabled = true;
	}
	
	@Override
	public void disable() {
		this.enabled = false;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}