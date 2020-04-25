package me.mastercapexd.games;

import java.util.Collection;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Plot;

public class PlotLimitedGameBox<T extends Team> extends BaseGameBox<T> {

	private final Plot plot;
	
	public PlotLimitedGameBox(@Nonnull String id, Collection<T> teams, @Nonnull Plot plot) {
		super(id, teams);
		this.plot = plot;
	}
	
	@Nonnull
	public Plot getPlot() {
		return plot;
	}
}