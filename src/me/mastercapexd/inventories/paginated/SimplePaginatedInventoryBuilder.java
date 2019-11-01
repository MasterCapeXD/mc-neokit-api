package me.mastercapexd.inventories.paginated;

import javax.annotation.Nonnull;

import org.bukkit.plugin.Plugin;

import me.mastercapexd.inventories.InventoryBase;
import me.mastercapexd.inventories.personal.SimplePersonalViewInventoryBuilder;

public class SimplePaginatedInventoryBuilder extends SimplePersonalViewInventoryBuilder implements PaginatedInventoryBuilder {

	protected Integer[] contentSlots;
	
	public SimplePaginatedInventoryBuilder(@Nonnull Plugin plugin) {
		super(plugin);
	}
	
	@Nonnull
	@Override
	public PaginatedInventoryBuilder setContentSlotsScheme(@Nonnull Integer[] slots) {
		this.contentSlots = slots;
		return this;
	}
	
	@Nonnull
	@Override
	public PaginatedInventory build() {
		InventoryBase inventoryBase = baseBuilder.build();
		return new SimplePaginatedInventory(inventoryBase.getPlugin(), inventoryBase.getRows(), inventoryBase.getOpeningAction(), inventoryBase.getClosingAction(), titleApplier, defaultIcons, contentSlots);
	}
}