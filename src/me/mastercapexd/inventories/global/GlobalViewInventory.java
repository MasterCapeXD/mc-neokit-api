package me.mastercapexd.inventories.global;

import javax.annotation.Nonnull;

import me.mastercapexd.inventories.InventoryBase;
import me.mastercapexd.inventories.icon.Icon;

public interface GlobalViewInventory extends InventoryBase {

	@Nonnull
	String getTitle();
	
	@Nonnull
	Icon getIcon(int slot);
	
	@Nonnull
	GlobalViewInventory setIcon(int slot, @Nonnull Icon icon);
}