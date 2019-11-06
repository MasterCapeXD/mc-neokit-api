package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Cuboid;

public class CuboidLimitedGameBox extends BaseGameBox {

	private final Cuboid cuboid;
	
	public CuboidLimitedGameBox(@Nonnull String id, @Nonnull Collection<Team> teams, @Nonnull Map<String, GameAction> actions, @Nonnull Cuboid cuboid) {
		super(id, teams, actions);
		this.cuboid = cuboid;
	}
	
	@Nonnull
	public Cuboid getCuboid() {
		return cuboid;
	}
	
	public static class Builder extends me.mastercapexd.games.BaseGameBox.Builder {

		private final Cuboid cuboid;
		
		public Builder(@Nonnull String id, @Nonnull Cuboid cuboid) {
			super(id);
			this.cuboid = cuboid;
		}
		
		@Nonnull
		@Override
		public GameBox build() {
			return new CuboidLimitedGameBox(id, teams, actions, cuboid);
		}
	}
}