package me.mastercapexd.inventories.icon;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mastercapexd.inventories.ClickData;

public interface Icon {

	static final Icon EMPTY_REPLACEABLE = of(new ItemStack(Material.AIR), data -> {}, true);
	static final Icon EMPTY = of(new ItemStack(Material.AIR));
	
	@Nonnull
	static Icon of(@Nonnull ItemStack itemStack) {
		return of(itemStack, data -> {});
	}
	
	@Nonnull
	static Icon of(@Nonnull ItemStack itemStack, @Nonnull Consumer<ClickData> consumer) {
		return of(itemStack, consumer, false);
	}
	
	@Nonnull
	static Icon of(@Nonnull ItemStack itemStack, @Nonnull Consumer<ClickData> consumer, boolean takeable) {
		return new AbstractIcon(consumer, takeable) {
			
			@Override
			public ItemStack getIcon() {
				return itemStack;
			}
		};
	}
	
	@Nonnull
	ItemStack getIcon();
	
	@Nonnull
	Consumer<ClickData> getClickAction();
	
	boolean isTakeable();
}