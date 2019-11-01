package me.mastercapexd.commons.colors;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import com.google.common.collect.Lists;

public final class Colors {

	private Colors() { throw new UnsupportedOperationException("This class cannot be instantiated!"); }
	
	public static String colorize(@Nonnull String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	public static List<String> colorize(@Nonnull List<String> listOfStrings) {
		List<String> list = Lists.newArrayList();
		for (String str : listOfStrings)
			list.add(colorize(str));
		return list;
	}
	
	public static String decolorize(@Nonnull String str) {
		return ChatColor.stripColor(str);
	}
	
	public static List<String> decolorize(@Nonnull List<String> listOfStrings) {
		List<String> list = Lists.newArrayList();
		for (String str : listOfStrings)
			list.add(decolorize(str));
		return list;
	}
	
	@Nonnull
	public static Material getMaterialByColor(DyeColor color, String suffix) {
		return Material.getMaterial(color + "_" + suffix);
	}
}