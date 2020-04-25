package me.mastercapexd.inventories;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.mastercapexd.inventories.InventoryData;
import me.mastercapexd.inventories.icon.AbstractIcon;
import me.mastercapexd.inventories.icon.Icon;

public class InventoryBaseBuilder implements InventoryBuilder<InventoryBaseBuilder, InventoryBase> {

	private final Plugin plugin;
	private int rows = 1;
	private Consumer<InventoryData> opening = data -> {}, closing = data -> {};
	private boolean handleBottomClicks;
	
	public InventoryBaseBuilder(@Nonnull Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Nonnull
	@Override
	public InventoryBaseBuilder setRows(int rows) {
		this.rows = rows;
		return this;
	}
	
	@Nonnull
	@Override
	public InventoryBaseBuilder addOpeningAction(@Nonnull Consumer<InventoryData> consumer) {
		this.opening = this.opening.andThen(consumer);
		return this;
	}
	
	@Nonnull
	@Override
	public InventoryBaseBuilder addClosingAction(@Nonnull Consumer<InventoryData> consumer) {
		this.closing = this.closing.andThen(consumer);
		return this;
	}
	
	@Nonnull
	@Override
	public InventoryBaseBuilder handleBottomClicks(boolean handleBottomClicks) {
		this.handleBottomClicks = handleBottomClicks;
		return this;
	}
	
	@Nonnull
	@Override
	public InventoryBase build() {
		return new AbstractInventory(plugin, rows, opening, closing, handleBottomClicks) {
			
			@Override
			public void refresh(Player player) {}
			
			@Override
			public void open(Player player) {}
			
			@Override
			public void close(Player player) {}
			
			@Override
			public Collection<Player> getCurrentViewers() {
				return Collections.emptySet();
			}
			
			@Override
			public Icon getIcon(ClickData data) {
				return AbstractIcon.EMPTY_REPLACEABLE;
			}
		};
	}
}