package me.mastercapexd.games;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;

import me.mastercapexd.commons.Identifiable;

public interface GameEntity<E extends Entity> extends Identifiable<String> {

	@Nullable
	GameBox getGameBox();
	
	@Nullable
	Team getTeam();
	
	boolean isLiving();
	
	@Nonnull
	E getSource();
}