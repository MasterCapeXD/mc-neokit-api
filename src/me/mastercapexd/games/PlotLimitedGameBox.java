package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Plot;

public class PlotLimitedGameBox extends BaseGameBox {

	private final Plot plot;
	
	public PlotLimitedGameBox(@Nonnull String id, @Nonnull Collection<Team> teams, @Nonnull Map<String, GameAction> actions, @Nonnull Plot plot) {
		super(id, teams, actions);
		this.plot = plot;
	}
	
	@Nonnull
	public Plot getPlot() {
		return plot;
	}
	
	public static class Builder extends me.mastercapexd.games.BaseGameBox.Builder {

		private final Plot plot;
		
		public Builder(@Nonnull String id, @Nonnull Plot plot) {
			super(id);
			this.plot = plot;
		}
		
		@Nonnull
		@Override
		public GameBox build() {
			return new PlotLimitedGameBox(id, teams, actions, plot);
		}
	}
}