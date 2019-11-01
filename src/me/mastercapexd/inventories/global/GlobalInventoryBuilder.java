package me.mastercapexd.inventories.global;

import javax.annotation.Nonnull;

import me.mastercapexd.inventories.InventoryBuilder;
import me.mastercapexd.inventories.icon.Icon;

public interface GlobalInventoryBuilder extends InventoryBuilder<GlobalInventoryBuilder, GlobalViewInventory> {

	@Nonnull
	GlobalInventoryBuilder setTitle(@Nonnull String title);
	
	@Nonnull
	GlobalInventoryBuilder withIcon(int slot, @Nonnull Icon icon);
}