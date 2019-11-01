package me.mastercapexd.commons.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

@SuppressWarnings("unchecked")
public final class EventFilters {

	private static final Predicate<? extends Cancellable> IGNORE_CANCELLED = e -> !e.isCancelled();
	private static final Predicate<? extends Cancellable> IGNORE_UNCANCELLED = Cancellable::isCancelled;
	private static final Predicate<? extends PlayerLoginEvent> IGNORE_DISALLOWED_LOGIN = e -> e.getResult() == PlayerLoginEvent.Result.ALLOWED;
	private static final Predicate<? extends AsyncPlayerPreLoginEvent> IGNORE_DISALLOWED_PRE_LOGIN = e -> e.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED;
	
	private static final Predicate<? extends PlayerMoveEvent> IGNORE_SAME_BLOCK = e ->
	e.getFrom().getBlockX() != e.getTo().getBlockX() ||
	e.getFrom().getBlockZ() != e.getTo().getBlockZ() ||
	e.getFrom().getBlockY() != e.getTo().getBlockY() ||
	!e.getFrom().getWorld().equals(e.getTo().getWorld());
    
	private static final Predicate<? extends PlayerMoveEvent> IGNORE_SAME_BLOCK_AND_Y = e ->
	e.getFrom().getBlockX() != e.getTo().getBlockX() ||
	e.getFrom().getBlockZ() != e.getTo().getBlockZ() ||
	!e.getFrom().getWorld().equals(e.getTo().getWorld());
	
	private static final Predicate<? extends PlayerMoveEvent> IGNORE_SAME_CHUNK = e ->
	(e.getFrom().getBlockX() >> 4) != (e.getTo().getBlockX() >> 4) ||
	(e.getFrom().getBlockZ() >> 4) != (e.getTo().getBlockZ() >> 4) ||
	!e.getFrom().getWorld().equals(e.getTo().getWorld());
	
	@Nonnull
	public static <T extends Cancellable> Predicate<T> ignoreCancelled() {
		return (Predicate<T>) IGNORE_CANCELLED;
	}
	
	@Nonnull
	public static <T extends Cancellable> Predicate<T> ignoreNotCancelled() {
		return (Predicate<T>) IGNORE_UNCANCELLED;
	}
	
	@Nonnull
	public static <T extends PlayerLoginEvent> Predicate<T> ignoreDisallowedLogin() {
		return (Predicate<T>) IGNORE_DISALLOWED_LOGIN;
	}
	
	@Nonnull
	public static <T extends AsyncPlayerPreLoginEvent> Predicate<T> ignoreDisallowedPreLogin() {
		return (Predicate<T>) IGNORE_DISALLOWED_PRE_LOGIN;
	}
	
	@Nonnull
	public static <T extends PlayerMoveEvent> Predicate<T> ignoreSameBlock() {
		return (Predicate<T>) IGNORE_SAME_BLOCK;
	}
	
	@Nonnull
	public static <T extends PlayerMoveEvent> Predicate<T> ignoreSameBlockAndY() {
		return (Predicate<T>) IGNORE_SAME_BLOCK_AND_Y;
	}
	
	@Nonnull
	public static <T extends PlayerMoveEvent> Predicate<T> ignoreSameChunk() {
		return (Predicate<T>) IGNORE_SAME_CHUNK;
	}
	
	@Nonnull
	public static <T extends PlayerEvent> Predicate<T> playerHasPermission(String permission) {
		return e -> e.getPlayer().hasPermission(permission);
	}
	
	private EventFilters() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}
