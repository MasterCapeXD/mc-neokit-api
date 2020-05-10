package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public final class Commands {

	public static <T extends CommandSender> CommandBuilder<T> newCommandBuilder(@Nonnull Class<T> senderTypeClass, @Nonnull Plugin plugin, @Nonnull String name) {
		return new SimpleCommandBuilder<T>(plugin, name);
	}
	
	public static <T extends CommandSender> CommandBuilder<T> newCommandBuilder(@Nonnull Class<T> senderTypeClass, @Nonnull Plugin plugin, @Nonnull String holder, @Nonnull String name) {
		return new SimpleCommandBuilder<T>(plugin, holder, name);
	}
	
	public static <T extends CommandSender> CommandArgumentBuilder<T> newArgumentBuilder(@Nonnull Class<T> senderTypeClass, @Nonnull String name) {
		return new SimpleCommandArgumentBuilder<T>(name, null);
	}
	
	private Commands() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}