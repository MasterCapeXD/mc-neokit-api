package me.mastercapexd.games;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Entity;

public class BaseEntity<E extends Entity> implements GameEntity<E> {

	private final String id;
	private final E entity;
	private final GameData data;
	protected GameBox<?> box;
	protected Team team;
	protected boolean living;
	
	public BaseEntity(@Nonnull String id, @Nonnull E entity) {
		this.id = id;
		this.entity = entity;
		this.data = new BaseGameData();
	}
	
	@Nonnull
	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Nullable
	@Override
	public GameBox<?> getGameBox() {
		return box;
	}
	
	@Nullable
	@Override
	public Team getTeam() {
		return team;
	}
	
	@Override
	public boolean isLiving() {
		return living;
	}
	
	@Nonnull
	@Override
	public E getSource() {
		return entity;
	}
	
	@Override
	public GameData getData() {
		return data;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj != null) {
			if (obj instanceof GameEntity<?>) {
				GameEntity<?> entity = (GameEntity<?>) obj;
				return this.id.equals(entity.getIdentifier());
			} else if (obj instanceof Entity)
				return this.getSource().equals(obj);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}