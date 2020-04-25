package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public class BaseTeam implements Team {

	private final String id, displayName;
	private final Map<Entity, GameEntity<?>> entities = Maps.newHashMap();
	private final GameData data;
	private final int slots;
	
	public BaseTeam(@Nonnull String id, @Nonnull String displayName, int slots) {
		this.id = id;
		this.displayName = displayName;
		this.data = new BaseGameData();
		this.slots = slots;
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
	
	@Nullable
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> GameEntity<T> getEntityNullable(T entity) {
		return (GameEntity<T>) entities.get(entity);
	}
	
	@Nonnull
	@Override
	public String getDisplayName() {
		return displayName;
	}
	
	@Override
	public int getTeamSlots() {
		return slots;
	}
	
	@Override
	public GameData getData() {
		return data;
	}
	
	public void addEntity(@Nonnull GameEntity<?> entity) {
		entities.put(entity.getSource(), entity);
	}
	
	public void removeEntity(@Nonnull GameEntity<?> entity) {
		entities.remove(entity.getSource());
	}
}