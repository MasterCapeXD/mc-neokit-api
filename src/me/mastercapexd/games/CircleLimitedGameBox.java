package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Circle;

public class CircleLimitedGameBox extends BaseGameBox {

	private final Circle circle;
	
	public CircleLimitedGameBox(@Nonnull String id, @Nonnull Collection<Team> teams, @Nonnull Map<String, GameAction> actions, @Nonnull Circle circle) {
		super(id, teams, actions);
		this.circle = circle;
	}
	
	@Nonnull
	public Circle getCircle() {
		return circle;
	}
	
	public static class Builder extends me.mastercapexd.games.BaseGameBox.Builder {

		private final Circle circle;
		
		public Builder(@Nonnull String id, @Nonnull Circle circle) {
			super(id);
			this.circle = circle;
		}
		
		@Nonnull
		@Override
		public GameBox build() {
			return new CircleLimitedGameBox(id, teams, actions, circle);
		}
	}
}