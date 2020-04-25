package me.mastercapexd.games;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public class BaseGame<T extends Team> implements Game<T> {

	private final String id;
	private final Plugin plugin;
	private final Map<String, GameBox<T>> gameBoxMap = Maps.newHashMap();
	
	private boolean active;
	
	public BaseGame(@Nonnull String id, @Nonnull Plugin plugin) {
		this.id = id;
		this.plugin = plugin;
	}
	
	@Nonnull
	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Nonnull
	@Override
	public Plugin getPlugin() {
		return plugin;
	}
	
	@Nonnull
	@Override
	public Collection<GameBox<T>> getGameBoxes() {
		return ImmutableSet.copyOf(gameBoxMap.values());
	}
	
	@Override
	public void addGameBox(GameBox<T> box) {
		gameBoxMap.put(box.getIdentifier(), box);
	}
	
	@Override
	public void removeGameBox(String id) {
		gameBoxMap.remove(id);
	}
	
	@Nullable
	@Override
	public GameBox<T> getGameBox(String id) {
		return gameBoxMap.get(id);
	}
	
	@Override
	public void enable() {
		this.active = true;
	}
	
	@Override
	public void disable() {
		this.active = false;
		for (GameBox<T> box : getGameBoxes())
			box.disable();
	}
	
	@Override
	public final boolean isEnabled() {
		return active;
	}
}