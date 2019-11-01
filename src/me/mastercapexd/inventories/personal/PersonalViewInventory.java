package me.mastercapexd.inventories.personal;

import java.util.function.BiFunction;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

import me.mastercapexd.inventories.InventoryBase;
import me.mastercapexd.inventories.icon.Icon;

public interface PersonalViewInventory extends InventoryBase {

	@Nonnull
	<T extends PersonalViewInventory> BiFunction<Player, T, String> getTitleApplier();
	
	@Nonnull
	Icon getIcon(@Nonnull Player player, int slot);
	
	@Nonnull
	PersonalViewInventory setIcon(@Nonnull Player player, int slot, @Nonnull Icon icon);
	
	@Nonnull
	PersonalViewInventory setIcon(int slot, @Nonnull Icon icon);
	
	default void refreshAll() {
		for (Player player : getCurrentViewers())
			refresh(player);
	}
}