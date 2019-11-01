package me.mastercapexd.inventories.personal;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import me.mastercapexd.inventories.AbstractInventory;
import me.mastercapexd.inventories.InventoryData;
import me.mastercapexd.inventories.InventoryView;
import me.mastercapexd.inventories.icon.Icon;

public class SimplePersonalViewInventory extends AbstractInventory implements PersonalViewInventory {

	private final BiFunction<Player, PersonalViewInventory, String> titleApplier;
	private final Map<Player, InventoryView> viewers = Maps.newHashMap();
	private final List<Pair<Function<Player, Integer>, Function<Player, Icon>>> defaultIcons;
	protected final Map<Player, Map<Integer, Icon>> icons;
	
	public SimplePersonalViewInventory(@Nonnull Plugin plugin, int rows, @Nonnull Consumer<InventoryData> opening,
			@Nonnull Consumer<InventoryData> closing, @Nonnull BiFunction<Player, PersonalViewInventory, String> titleApplier, @Nonnull List<Pair<Function<Player, Integer>, Function<Player, Icon>>> defaultIcons) {
		super(plugin, rows, opening, closing);
		this.titleApplier = titleApplier;
		this.defaultIcons = defaultIcons;
		this.icons = Maps.newHashMap();
	}
	
	@Nonnull
	@Override
	public Collection<Player> getCurrentViewers() {
		return ImmutableSet.copyOf(viewers.keySet());
	}
	
	@Override
	public void open(@Nonnull Player player) {
		if (isViewing(player))
			return;
		
		if (!icons.containsKey(player))
			icons.put(player, Maps.newHashMap());
		
		for (Pair<Function<Player, Integer>, Function<Player, Icon>> pair : defaultIcons) {
			int slot = pair.getLeft().apply(player);
			
			if (icons.get(player).containsKey(slot) && icons.get(player).get(slot) != Icon.EMPTY)
				continue;
			
			icons.get(player).put(slot, pair.getRight().apply(player));
		}
		
		InventoryView view = new InventoryView(this, titleApplier.apply(player, this), getRows());
		
		viewers.put(player, view);
		refresh(player);
		listenView(player);
		player.openInventory(view.getInventory());
	}
	
	@Override
	public void close(@Nonnull Player player) {
		viewers.remove(player);
	}
	
	@Override
	public void refresh(@Nonnull Player player) {
		Preconditions.checkState(isViewing(player));
		Preconditions.checkState(icons.get(player) != null, "Player icon map cannot be null!");
		
		for (Entry<Integer, Icon> entry : icons.get(player).entrySet())
			viewers.get(player).getInventory().setItem(entry.getKey(), entry.getValue().getIcon());
	}
	
	@Nonnull
	@Override
	public BiFunction<Player, PersonalViewInventory, String> getTitleApplier() {
		return titleApplier;
	}
	
	@Nonnull
	@Override
	public Icon getIcon(@Nonnull Player player, int slot) {
		Preconditions.checkState(isViewing(player));
		Preconditions.checkState(icons.get(player) != null, "Player icon map cannot be null!");
		
		Icon icon = icons.get(player).get(slot);
		if (icon == null) {
			icons.get(player).put(slot, Icon.EMPTY);
			return getIcon(player, slot);
		}
		
		return icon;
	}
	
	@Nonnull
	@Override
	public PersonalViewInventory setIcon(@Nonnull Player player, int slot, @Nonnull Icon icon) {
		Preconditions.checkState(icons.get(player) != null, "Player icon map cannot be null!");
		
		icons.get(player).put(slot, icon);
		return this;
	}
	
	@Nonnull
	@Override
	public PersonalViewInventory setIcon(int slot, @Nonnull Icon icon) {
		icons.keySet().stream().forEach(player -> setIcon(player, slot, icon));
		return this;
	}
}