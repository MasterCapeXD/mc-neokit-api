package me.mastercapexd.games;

import java.util.Collection;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Identifiable;

public interface GameBox<T extends Team> extends Identifiable<String>, EntityRepository {

	@Nonnull
	Collection<T> getRegisteredTeams();
	
	@Nonnull
	T getTeam(@Nonnull String id);
	
	@Nonnull
	GameData getData();
	
	void enable();
	
	void disable();
	
	boolean isEnabled();
}