package me.mastercapexd.games;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;

import me.mastercapexd.commons.Identifiable;

public interface GameAction extends Identifiable<String> {

	static GameAction of(@Nonnull String id, @Nonnull Consumer<GameEntity<? extends Entity>> consumer) {
		return new GameAction() {
			
			@Override
			public String getIdentifier() {
				return id;
			}
			
			@Override
			public void execute(GameEntity<?> actor) {
				consumer.accept(actor);
			}
		};
	}
	
	void execute(@Nonnull GameEntity<? extends Entity> actor);
}