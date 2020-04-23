package me.mastercapexd.inventories.icon;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mastercapexd.inventories.ClickData;

public interface Icon {

	static final Icon EMPTY_REPLACEABLE = of(new ItemStack(Material.AIR), data -> false);
	static final Icon EMPTY = of(new ItemStack(Material.AIR));
	
	@Nonnull
	static Icon of(@Nonnull ItemStack itemStack) {
		return of(itemStack, data -> true);
	}
	
	@Nonnull
	static Icon of(@Nonnull ItemStack itemStack, boolean cancel) {
		return of(itemStack, data -> cancel);
	}
	
	@Nonnull
	static Icon of(@Nonnull ItemStack itemStack, @Nonnull Predicate<ClickData> predicate) {
		return new AbstractIcon(predicate) {
			
			@Override
			public ItemStack getIcon() {
				return itemStack;
			}
		};
	}
	
	@Nonnull
	ItemStack getIcon();
	
	@Nonnull
	Predicate<ClickData> getClickAction();
}