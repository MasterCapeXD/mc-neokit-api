package me.mastercapexd.inventories;

import javax.annotation.Nonnull;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface InventoryData {

	@Nonnull
	static InventoryData create(@Nonnull HumanEntity entity, @Nonnull Inventory inventory) {
		return new InventoryData() {
			
			@Override
			public Player getIssuer() {
				return (Player) entity;
			}
			
			@Override
			public Inventory getInventory() {
				return inventory;
			}
		};
	}
	
	@Nonnull
	Inventory getInventory();
	
	@Nonnull
	Player getIssuer();
	
	@Nonnull
	default InventoryBase getInventoryBase() {
		return ((InventoryView) getInventory().getHolder()).getOwner();
	}
}