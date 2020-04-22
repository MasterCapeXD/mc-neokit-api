package me.mastercapexd.games;

import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.Maps;

import me.mastercapexd.commons.Events;
import me.mastercapexd.commons.IdentifierType;
import me.mastercapexd.commons.events.SingleEventSubscriptionBuilder;

public class Games {

	private static final Map<String, Game> GAMES = Maps.newHashMap();
	private static final Map<String, GamePlayer> PLAYERS = Maps.newHashMap();
	private static final SingleEventSubscriptionBuilder<?> JOIN, QUIT;
	
	private static IdentifierType identifierType = IdentifierType.NAME;
	
	static {
		JOIN = Events.subscribe(PlayerJoinEvent.class, EventPriority.LOWEST)
		.handle(event -> {
			String id = identifierType == IdentifierType.NAME ? event.getPlayer().getName() : event.getPlayer().getUniqueId().toString();
			PLAYERS.put(id, new BasePlayer(id, event.getPlayer()));
		});
		
		QUIT = Events.subscribe(PlayerQuitEvent.class, EventPriority.MONITOR)
		.handle(event -> {
			String id = identifierType == IdentifierType.NAME ? event.getPlayer().getName() : event.getPlayer().getUniqueId().toString();
			PLAYERS.remove(id);
		});
	}
	
	public static void registerGame(@Nonnull Game game) {
		if (!JOIN.registered())
			JOIN.register(game.getPlugin());
		if (!QUIT.registered())
			QUIT.register(game.getPlugin());
		
		GAMES.put(game.getIdentifier(), game);
	}
	
	public static void unregisterGame(@Nonnull String id) {
		if (GAMES.containsKey(id))
			GAMES.get(id).disable();
		GAMES.remove(id);
	}
	
	public static void setIdentifierType(@Nonnull IdentifierType idType) {
		identifierType = idType;
	}
	
	@Nonnull
	public static IdentifierType getCurrentIdentifierType() {
		return identifierType;
	}
	
	@Nonnull
	public static GamePlayer getGamePlayer(@Nonnull Player player) {
		return PLAYERS.getOrDefault(getCurrentIdentifierType() == IdentifierType.NAME ? player.getName() : player.getUniqueId().toString(), null);
	}
	
	@Nonnull
	public static GamePlayer getGamePlayer(@Nonnull String id) {
		return PLAYERS.getOrDefault(id, null);
	}
}