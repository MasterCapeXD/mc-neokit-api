package me.mastercapexd.games;

import java.util.Collection;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Cuboid;

public class CuboidLimitedGameBox<T extends Team> extends BaseGameBox<T> {

	private final Cuboid cuboid;
	
	public CuboidLimitedGameBox(@Nonnull String id, Collection<T> teams, @Nonnull Cuboid cuboid) {
		super(id, teams);
		this.cuboid = cuboid;
	}
	
	@Nonnull
	public Cuboid getCuboid() {
		return cuboid;
	}
}