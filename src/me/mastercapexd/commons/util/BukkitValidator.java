package me.mastercapexd.commons.util;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public final class BukkitValidator {

	public static boolean isConsoleSender(CommandSender sender) {
		return sender instanceof ConsoleCommandSender;
	}
}