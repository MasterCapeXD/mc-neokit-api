package me.mastercapexd.inventories.personal;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

import me.mastercapexd.inventories.InventoryBase;
import me.mastercapexd.inventories.InventoryBaseBuilder;
import me.mastercapexd.inventories.InventoryData;
import me.mastercapexd.inventories.icon.Icon;

public class SimplePersonalViewInventoryBuilder implements PersonalInventoryBuilder {

	protected final List<Pair<Function<Player, Integer>, Function<Player, Icon>>> defaultIcons = Lists.newArrayList();
	protected InventoryBaseBuilder baseBuilder;
	protected BiFunction<Player, PersonalViewInventory, String> titleApplier = (player, inventory) -> player.getName() + "'s Inventory";
	
	public SimplePersonalViewInventoryBuilder(@Nonnull Plugin plugin) {
		this.baseBuilder = new InventoryBaseBuilder(plugin);
	}
	
	@Nonnull
	@Override
	public PersonalInventoryBuilder setRows(int rows) {
		baseBuilder.setRows(rows);
		return this;
	}
	
	@Nonnull
	@Override
	public PersonalInventoryBuilder addOpeningAction(@Nonnull Consumer<InventoryData> consumer) {
		baseBuilder.addOpeningAction(consumer);
		return this;
	}
	
	@Nonnull
	@Override
	public PersonalInventoryBuilder addClosingAction(@Nonnull Consumer<InventoryData> consumer) {
		baseBuilder.addClosingAction(consumer);
		return this;
	}
	
	@Override
	public PersonalInventoryBuilder handleBottomClicks(boolean handleBottomClicks) {
		baseBuilder.handleBottomClicks(handleBottomClicks);
		return this;
	}
	
	
	@Nonnull
	@Override
	public PersonalInventoryBuilder setTitleApplier(BiFunction<Player, PersonalViewInventory, String> titleApplier) {
		this.titleApplier = titleApplier;
		return this;
	}
	
	@Nonnull
	@Override
	public PersonalInventoryBuilder withIcon(@Nonnull Function<Player, Integer> slot, @Nonnull Function<Player, Icon> iconApplier) {
		defaultIcons.add(Pair.of(slot, iconApplier));
		return this;
	}
	
	@Nonnull
	@Override
	public PersonalViewInventory build() {
		InventoryBase inventoryBase = baseBuilder.build();
		return new SimplePersonalViewInventory(inventoryBase.getPlugin(), inventoryBase.getRows(), inventoryBase.getOpeningAction(), inventoryBase.getClosingAction(), inventoryBase.handleBottomClicks(), titleApplier, defaultIcons);
	}
}