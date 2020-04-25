package me.mastercapexd.inventories;

import javax.annotation.Nonnull;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public interface ClickData extends InventoryData {

	@Nonnull
	static ClickData create(@Nonnull HumanEntity entity, @Nonnull Inventory inventory, @Nonnull ClickType clickType, int slot, @Nonnull InventoryAction action, Inventory bottom, boolean isBottomClick) {
		return new ClickData() {
			
			@Override
			public Player getIssuer() {
				return (Player) entity;
			}
			
			@Override
			public Inventory getInventory() {
				return inventory;
			}
			
			@Override
			public ClickType getType() {
				return clickType;
			}
			
			@Override
			public int getClickedSlot() {
				return slot;
			}
			
			@Override
			public InventoryAction getAction() {
				return action;
			}
			
			@Override
			public boolean isBottomClick() {
				return isBottomClick;
			}
			
			@Override
			public Inventory getBottom() {
				return bottom;
			}
		};
	}
	
	@Nonnull
	ClickType getType();
	
	@Nonnull
	InventoryAction getAction();
	
	int getClickedSlot();
	
	boolean isBottomClick();
	
	Inventory getBottom();
}