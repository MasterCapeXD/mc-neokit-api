package me.mastercapexd.inventories.paginated;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import me.mastercapexd.inventories.ClickData;
import me.mastercapexd.inventories.InventoryData;
import me.mastercapexd.inventories.icon.Icon;
import me.mastercapexd.inventories.personal.PersonalViewInventory;
import me.mastercapexd.inventories.personal.SimplePersonalViewInventory;

public class SimplePaginatedInventory extends SimplePersonalViewInventory implements PaginatedInventory {

	private final Collection<Integer> contentSlots;
	private final Map<Player, Integer> playerPageMap = Maps.newHashMap();
	
	public SimplePaginatedInventory(@Nonnull Plugin plugin, int rows, @Nonnull Consumer<InventoryData> opening,
			@Nonnull Consumer<InventoryData> closing, @Nonnull BiFunction<Player, PersonalViewInventory, String> titleApplier,
			@Nonnull List<Pair<Function<Player, Integer>, Function<Player, Icon>>> defaultIcons, @Nonnull Integer[] contentSlots) {
		super(plugin, rows, opening, closing, titleApplier, defaultIcons);
		this.contentSlots = ImmutableSet.copyOf(contentSlots);
	}
	
	@Nonnull
	@Override
	public Collection<Integer> getContentSlotsScheme() {
		return contentSlots;
	}
	
	@Nonnull
	@Override
	public List<Icon> getContents(Player player, int page) {
		List<Icon> icons = Lists.newArrayList();
		for (int slot : contentSlots) {
			Icon icon = getIconAt(player, page, slot);
			if (icon != Icon.EMPTY && icon.getIcon().getType() != Material.AIR)
				icons.add(getIconAt(player, page, slot));
		}
		return icons;
	}
	
	@Override
	public void addContents(Player player, List<Icon> icons) {
		int freePage = getPages(player), filledPageSlots = getContents(player, freePage).size();
		Integer[] slots = contentSlots.toArray(new Integer[contentSlots.size()]);
		int listIndex = 0;
		for (int i = filledPageSlots; i < contentSlots.size() && listIndex < icons.size(); i++, listIndex++)
			setIcon(player, slots[i], icons.get(listIndex));
		
		if (listIndex >= icons.size())
			return;
		
		List<Icon> nextPageIcons = icons.subList(listIndex, icons.size() - 1);
		
		if (!nextPageIcons.isEmpty())
			addContents(player, nextPageIcons);
	}
	
	@Override
	public void addContents(List<Icon> icons) {
		for (Player player : this.icons.keySet())
			addContents(player, icons);
	}
	
	@Override
	public void removeContents(Player player, List<Icon> icons) {
		Preconditions.checkArgument(this.icons.containsKey(player), "Player contents are null!");
		Map<Integer, Icon> iconMap = this.icons.get(player);
		
		Collection<Integer> slotsToReset = Sets.newHashSet();
		for (int slot : iconMap.keySet())
			for (Icon icon : icons)
				if (this.icons.get(player).get(slot).equals(icon))
					slotsToReset.add(slot);
		
		for (int slot : slotsToReset)
			this.icons.get(player).put(slot, Icon.EMPTY);
	}
	
	@Override
	public void removeContents(List<Icon> icons) {
		for (Player player : this.icons.keySet())
			removeContents(player, icons);
	}
	
	@Override
	public void clearContents(Player player) {
		Preconditions.checkArgument(this.icons.containsKey(player), "Player contents are null!");
		this.icons.get(player).clear();
	}
	
	@Override
	public void clearContents() {
		for (Player player : this.icons.keySet())
			clearContents(player);
	}
	
	@Override
	public int getCurrentPage(Player player) {
		return playerPageMap.getOrDefault(player, 1);
	}
	
	@Override
	public void setCurrentPage(Player player, int page) {
		playerPageMap.put(player, page);
	}
	
	@Override
	public int getPages(Player player) {
		int pages = 1;
		while (true) {
			int size = getContents(player, pages).size();
			if (size < getContentSlotsScheme().size())
				break;
			if (getContents(player, pages + 1).size() == 0)
				break;
			pages++;
		}
		return pages;
	}
	
	@Override
	public Icon getIcon(ClickData data) {
		return getIconAt(data.getIssuer(), getCurrentPage(data.getIssuer()), data.getClickedSlot());
	}
}