package me.mastercapexd.games;

import java.util.Collection;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Identifiable;

public interface GameBox extends Identifiable<String>, EntityRepository, ActionRepository {

	@Nonnull
	Collection<Team> getRegisteredTeams();
	
	@Nonnull
	Team getTeam(@Nonnull String id);
}