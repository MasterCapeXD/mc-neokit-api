package me.mastercapexd.inventories.personal;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

import me.mastercapexd.inventories.InventoryBuilder;
import me.mastercapexd.inventories.icon.Icon;

public interface PersonalInventoryBuilder extends InventoryBuilder<PersonalInventoryBuilder, PersonalViewInventory> {

	@Nonnull
	PersonalInventoryBuilder setTitleApplier(@Nonnull BiFunction<Player, PersonalViewInventory, String> titleApplier);
	
	@Nonnull
	PersonalInventoryBuilder withIcon(@Nonnull Function<Player, Integer> slot, @Nonnull Function<Player, Icon> function);
	
	@Nonnull
	default PersonalInventoryBuilder withIcon(int slot, @Nonnull Function<Player, Icon> function) {
		return withIcon(player -> slot, function);
	}
	
	@Nonnull
	default PersonalInventoryBuilder setTitleApplier(@Nonnull Function<Player, String> titleApplier) {
		return setTitleApplier((player, inventory) -> titleApplier.apply(player));
	}
	
	@Nonnull
	default PersonalInventoryBuilder withIcon(int slot, @Nonnull Icon icon) {
		return withIcon(slot, player -> icon);
	}
}