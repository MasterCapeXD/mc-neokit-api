package me.mastercapexd.games;

import java.util.Collection;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Circle;

public class CircleLimitedGameBox<T extends Team> extends BaseGameBox<T> {

	private final Circle circle;
	
	public CircleLimitedGameBox(@Nonnull String id, Collection<T> teams, @Nonnull Circle circle) {
		super(id, teams);
		this.circle = circle;
	}
	
	@Nonnull
	public Circle getCircle() {
		return circle;
	}
}