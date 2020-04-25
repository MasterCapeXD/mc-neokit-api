package me.mastercapexd.inventories;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.Material;
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
	private final boolean handleBottomClicks;
	
	public AbstractInventory(@Nonnull Plugin plugin, int rows, @Nonnull Consumer<InventoryData> opening, @Nonnull Consumer<InventoryData> closing, boolean handleBottomClicks) {
		this.plugin = plugin;
		this.rows = rows;
		this.opening = opening;
		this.closing = closing;
		this.handleBottomClicks = handleBottomClicks;
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
	
	@Override
	public boolean handleBottomClicks() {
		return handleBottomClicks;
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
					if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
						event.setCancelled(true);
					if (!handleBottomClicks())
						return;
				}
				
				InventoryView view = (InventoryView) event.getView().getTopInventory().getHolder();
				ClickData data = ClickData.create(player, view.getInventory(), event.getClick(), isBottomClick ? event.getSlot() : event.getRawSlot(), event.getAction(), event.getView().getBottomInventory(), isBottomClick);
				Icon icon = getIcon(data);
				event.setCancelled(icon.getClickAction().test(data));
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