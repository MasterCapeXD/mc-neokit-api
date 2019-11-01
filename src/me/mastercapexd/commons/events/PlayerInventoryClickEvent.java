package me.mastercapexd.commons.events;

import org.bukkit.event.inventory.InventoryClickEvent;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class PlayerInventoryClickEvent extends InventoryClickEvent {

	public static final BiMap<Integer, Integer> PLAYER_SLOTS = ImmutableBiMap. <Integer, Integer> builder()
			.put(5, 39)
			.put(6, 38)
			.put(7, 37)
			.put(8, 36)
			.put(36, 0)
			.put(37, 1)
			.put(38, 2)
			.put(39, 3)
			.put(40, 4)
			.put(41, 5)
			.put(42, 6)
			.put(43, 7)
			.put(44, 8)
			.put(45, 40)
			.build();
	
	public PlayerInventoryClickEvent(InventoryClickEvent event) {
		super(event.getView(), event.getSlotType(), event.getRawSlot(), event.getClick(), event.getAction(), event.getHotbarButton());
	}
	
	public int getClickedSlot() {
		return PLAYER_SLOTS.getOrDefault(getRawSlot(), getRawSlot());
	}
}