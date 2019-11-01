package me.mastercapexd.inventories.paginated;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import me.mastercapexd.inventories.icon.Icon;
import me.mastercapexd.inventories.personal.PersonalViewInventory;

public interface PaginatedInventory extends PersonalViewInventory {

	@Nonnull
	Collection<Integer> getContentSlotsScheme();
	
	@Nonnull
	List<Icon> getContents(@Nonnull Player player, int page);
	
	void addContents(@Nonnull Player player, @Nonnull List<Icon> icons);
	
	void addContents(@Nonnull List<Icon> icons);
	
	void removeContents(@Nonnull Player player, @Nonnull List<Icon> icons);
	
	void removeContents(@Nonnull List<Icon> icons);
	
	void clearContents(@Nonnull Player player);
	
	void clearContents();
	
	int getCurrentPage(@Nonnull Player player);
	
	void setCurrentPage(@Nonnull Player player, int page);
	
	int getPages(@Nonnull Player player);
	
	default Icon getIconAt(@Nonnull Player player, int page, int slot) {
		return getIcon(player, getRows() * 8 * (page - 1) + slot);
	}
	
	default List<Icon> getContents(@Nonnull Player player) {
		List<Icon> contents = Lists.newArrayList();
		int pages = getPages(player);
		for (int page = 1; page <= pages; page++)
			contents.addAll(getContents(player, page));
		return contents;
	}
	
	default void addContents(@Nonnull Player player, @Nonnull Icon... icons) {
		addContents(player, Lists.newArrayList(icons));
	}
	
	default void addContents(@Nonnull Icon... icons) {
		addContents(Lists.newArrayList(icons));
	}
	
	default void removeContents(@Nonnull Player player, @Nonnull Icon... icons) {
		removeContents(player, Lists.newArrayList(icons));
	}
	
	default void removeContents(@Nonnull Icon... icons) {
		removeContents(Lists.newArrayList(icons));
	}
}