package me.mastercapexd.games;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;

public class BaseEntity<E extends Entity> implements GameEntity<E> {

	private final String id;
	private final E entity;
	protected GameBox box;
	protected Team team;
	protected boolean living;
	
	public BaseEntity(@Nonnull String id, @Nonnull E entity) {
		this.id = id;
		this.entity = entity;
	}
	
	@Nonnull
	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Nullable
	@Override
	public GameBox getGameBox() {
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
}