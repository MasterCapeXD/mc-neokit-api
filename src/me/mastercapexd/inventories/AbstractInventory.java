package me.mastercapexd.inventories;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

import me.mastercapexd.commons.Events;
import me.mastercapexd.commons.events.ExpiryTestStage;
import me.mastercapexd.inventories.icon.Icon;

public abstract class AbstractInventory implements InventoryBase {

	private final Plugin plugin;
	private final int rows;
	private final Consumer<InventoryData> opening, closing;
	
	public AbstractInventory(@Nonnull Plugin plugin, int rows, @Nonnull Consumer<InventoryData> opening, @Nonnull Consumer<InventoryData> closing) {
		this.plugin = plugin;
		this.rows = rows;
		this.opening = opening;
		this.closing = closing;
	}
	
	@Nonnull
	@Override
	public Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public int getRows() {
		return rows;
	}
	
	@Nonnull
	@Override
	public Consumer<InventoryData> getOpeningAction() {
		return opening;
	}
	
	@Nonnull
	@Override
	public Consumer<InventoryData> getClosingAction() {
		return closing;
	}
	
	protected void listenView(@Nonnull Player player) {
		Events.subscribe(InventoryOpenEvent.class)
			.filter(event -> event.getInventory().getHolder() instanceof InventoryView)
			.filter(event -> event.getPlayer() == player)
			.limit(1)
			.handle(event -> {
				InventoryView view = (InventoryView) event.getInventory().getHolder();
				view.getOwner().getOpeningAction().accept(InventoryData.create(player, view.getInventory()));
			})
			.register(getPlugin());
	
		Events.subscribe(InventoryClickEvent.class)
			.filter(event -> event.getClickedInventory() != null)
			.filter(event -> event.getView().getTopInventory().getHolder() instanceof InventoryView)
			.filter(event -> event.getWhoClicked() == player)
			
			.expiresIf((subscription, event) -> {
				InventoryView view = (InventoryView) event.getView().getTopInventory().getHolder();
				return !view.getOwner().isViewing(player);
			}, ExpiryTestStage.POST_FILTER)
			
			.handle(event -> {
				boolean isBottomClick = event.getClickedInventory().equals(event.getView().getBottomInventory());
				if (isBottomClick) {
					event.setCancelled(true);
				}
				
				InventoryView view = (InventoryView) event.getView().getTopInventory().getHolder();
				ClickData data = ClickData.create(player, view.getInventory(), event.getClick(), event.getRawSlot(), event.getAction());
				Icon icon = getIcon(data);
				icon.getClickAction().accept(data);
				event.setCancelled(true);
			})
			.register(getPlugin());
		
		Events.subscribe(InventoryDragEvent.class)
		.filter(event -> event.getInventory() != null)
		.filter(event -> event.getInventory().getHolder() instanceof InventoryView)
		.filter(event -> event.getWhoClicked() == player)
		
		.expiresIf((subscription, event) -> {
			InventoryView view = (InventoryView) event.getInventory().getHolder();
			return !view.getOwner().isViewing(player);
		}, ExpiryTestStage.POST_FILTER)
		
		.handle(event -> event.setCancelled(true))
		.register(getPlugin());
	
		Events.subscribe(InventoryCloseEvent.class)
			.filter(event -> event.getInventory().getHolder() instanceof InventoryView)
			.filter(event -> event.getPlayer() == player)
			.limit(1)
			.handle(event -> {
				InventoryView view = (InventoryView) event.getInventory().getHolder();
				view.getOwner().getClosingAction().accept(InventoryData.create(player, view.getInventory()));
				view.getOwner().close(player);
			})
			.register(getPlugin());
	}
}