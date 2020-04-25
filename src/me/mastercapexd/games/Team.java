package me.mastercapexd.games;

import javax.annotation.Nonnull;

import me.mastercapexd.commons.Identifiable;

public interface Team extends Identifiable<String>, EntityRepository {

	@Nonnull
	String getDisplayName();
	
	int getTeamSlots();
	
	@Nonnull
	GameData getData();
}