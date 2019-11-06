package me.mastercapexd.games;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;

public interface EntityRepository {

	@Nonnull
	Collection<GameEntity<?>> getEntities();
	
	@Nullable
	<T extends Entity> GameEntity<T> getEntityNullable(T entity);
	
	@Nonnull
	default Collection<GamePlayer> getPlayers() {
		return getEntities().stream().filter(entity -> (entity instanceof GamePlayer)).map(entity -> (GamePlayer) entity).collect(Collectors.toSet());
	}
	
	default int getEntitiesCount() {
		return getEntities().size();
	}
	
	default int getPlayersCount() {
		return getPlayers().size();
	}
	
	@Nonnull
	default <T extends Entity> Optional<GameEntity<T>> getEntity(T entity) {
		return Optional.ofNullable(getEntityNullable(entity));
	}
}