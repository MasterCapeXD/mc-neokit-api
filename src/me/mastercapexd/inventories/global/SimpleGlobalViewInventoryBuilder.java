package me.mastercapexd.inventories.global;

import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

import me.mastercapexd.inventories.InventoryBase;
import me.mastercapexd.inventories.InventoryBaseBuilder;
import me.mastercapexd.inventories.InventoryData;
import me.mastercapexd.inventories.icon.Icon;

public class SimpleGlobalViewInventoryBuilder implements GlobalInventoryBuilder {

	private final Map<Integer, Icon> icons = Maps.newHashMap();
	private InventoryBaseBuilder baseBuilder;
	private String title = "Global Inventory";
	
	public SimpleGlobalViewInventoryBuilder(@Nonnull Plugin plugin) {
		this.baseBuilder = new InventoryBaseBuilder(plugin);
	}
	
	@Nonnull
	@Override
	public GlobalInventoryBuilder setRows(int rows) {
		baseBuilder.setRows(rows);
		return this;
	}
	
	@Nonnull
	@Override
	public GlobalInventoryBuilder addOpeningAction(@Nonnull Consumer<InventoryData> consumer) {
		baseBuilder.addOpeningAction(consumer);
		return this;
	}
	
	@Nonnull
	@Override
	public GlobalInventoryBuilder addClosingAction(@Nonnull Consumer<InventoryData> consumer) {
		baseBuilder.addClosingAction(consumer);
		return this;
	}
	
	@Nonnull
	@Override
	public GlobalInventoryBuilder setTitle(@Nonnull String title) {
		this.title = title;
		return this;
	}
	
	@Nonnull
	@Override
	public GlobalInventoryBuilder withIcon(int slot, @Nonnull Icon icon) {
		icons.put(slot, icon);
		return this;
	}
	
	@Nonnull
	@Override
	public GlobalViewInventory build() {
		InventoryBase inventoryBase = baseBuilder.build();
		return new SimpleGlobalViewInventory(inventoryBase.getPlugin(), inventoryBase.getRows(), inventoryBase.getOpeningAction(), inventoryBase.getClosingAction(), title, icons);
	}
}