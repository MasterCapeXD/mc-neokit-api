package me.mastercapexd.games;

import javax.annotation.Nullable;

public interface ActionRepository {

	@Nullable
	GameAction getGameAction(String id);
}