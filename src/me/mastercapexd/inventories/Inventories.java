package me.mastercapexd.inventories;

import javax.annotation.Nonnull;

import org.bukkit.plugin.Plugin;

import me.mastercapexd.inventories.global.GlobalInventoryBuilder;
import me.mastercapexd.inventories.global.SimpleGlobalViewInventoryBuilder;
import me.mastercapexd.inventories.paginated.PaginatedInventoryBuilder;
import me.mastercapexd.inventories.paginated.SimplePaginatedInventoryBuilder;
import me.mastercapexd.inventories.personal.PersonalInventoryBuilder;
import me.mastercapexd.inventories.personal.SimplePersonalViewInventoryBuilder;

public final class Inventories {

	@Nonnull
	public static GlobalInventoryBuilder newGlobalInventoryViewBuilder(@Nonnull Plugin plugin) {
		return new SimpleGlobalViewInventoryBuilder(plugin);
	}
	
	@Nonnull
	public static PersonalInventoryBuilder newPersonalInventoryViewBuilder(@Nonnull Plugin plugin) {
		return new SimplePersonalViewInventoryBuilder(plugin);
	}
	
	@Nonnull
	public static PaginatedInventoryBuilder newPaginatedInventoryViewBuilder(@Nonnull Plugin plugin) {
		return new SimplePaginatedInventoryBuilder(plugin);
	}
	
	private Inventories() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}