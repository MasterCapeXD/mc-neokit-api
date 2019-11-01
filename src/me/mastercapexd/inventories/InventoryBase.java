package me.mastercapexd.inventories;

import java.util.Collection;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface InventoryBase {

	@Nonnull
	Plugin getPlugin();
	
	int getRows();
	
	@Nonnull
	Collection<Player> getCurrentViewers();
	
	@Nonnull
	Consumer<InventoryData> getOpeningAction();
	
	@Nonnull
	Consumer<InventoryData> getClosingAction();
	
	void open(@Nonnull Player player);
	
	void close(@Nonnull Player player);
	
	void refresh(@Nonnull Player player);
	
	default boolean isViewing(@Nonnull Player player) {
		return getCurrentViewers().contains(player);
	}
}