package me.mastercapexd.inventories;

import java.util.Collection;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.mastercapexd.inventories.icon.Icon;

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
	
	Icon getIcon(ClickData data);
	
	default boolean isViewing(@Nonnull Player player) {
		return getCurrentViewers().contains(player);
	}
}