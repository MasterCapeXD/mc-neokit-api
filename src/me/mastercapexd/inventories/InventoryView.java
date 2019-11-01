package me.mastercapexd.inventories;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryView implements InventoryHolder {

	private final InventoryBase owner;
	private final Inventory inventory;
	
	public InventoryView(@Nonnull InventoryBase owner, @Nonnull String title, int rows) {
		this.owner = owner;
		this.inventory = Bukkit.createInventory(this, rows * 9, title);
	}
	
	@Nonnull
	public InventoryBase getOwner() {
		return owner;
	}
	
	@Nonnull
	@Override
	public Inventory getInventory() {
		return inventory;
	}
}