package me.mastercapexd.inventories.global;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

import me.mastercapexd.inventories.AbstractInventory;
import me.mastercapexd.inventories.InventoryData;
import me.mastercapexd.inventories.InventoryView;
import me.mastercapexd.inventories.icon.Icon;

public class SimpleGlobalViewInventory extends AbstractInventory implements GlobalViewInventory {

	private final String title;
	private final Map<Integer, Icon> icons;
	private final InventoryView view;
	
	public SimpleGlobalViewInventory(@Nonnull Plugin plugin, int rows, @Nonnull Consumer<InventoryData> opening,
			@Nonnull Consumer<InventoryData> closing, @Nonnull String title, @Nonnull Map<Integer, Icon> icons) {
		super(plugin, rows, opening, closing);
		this.title = title;
		this.icons = Maps.newHashMap(icons);
		this.view = new InventoryView(this, title, rows);
	}
	
	@Nonnull
	@Override
	public Collection<Player> getCurrentViewers() {
		return view.getInventory().getViewers().stream().map(entity -> (Player) entity).collect(Collectors.toSet());
	}
	
	@Override
	public void open(@Nonnull Player player) {
		refresh(player);
		listenView(player);
		player.openInventory(view.getInventory());
	}
	
	@Override
	public void close(@Nonnull Player player) {}
	
	@Override
	public void refresh(Player player) {
		view.getInventory().clear();
		for (Entry<Integer, Icon> entry : icons.entrySet())
			view.getInventory().setItem(entry.getKey(), entry.getValue().getIcon());
		player.updateInventory();
	}
	
	@Nonnull
	@Override
	public String getTitle() {
		return title;
	}
	
	@Nonnull
	@Override
	public Icon getIcon(int slot) {
		if (icons.containsKey(slot))
			return icons.get(slot);
		
		setIcon(slot, Icon.EMPTY);
		return getIcon(slot);
	}
	
	@Override
	public GlobalViewInventory setIcon(int slot, Icon icon) {
		icons.put(slot, icon);
		return this;
	}
}