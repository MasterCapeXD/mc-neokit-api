package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public final class Commands {

	public static <T extends CommandSender> CommandBuilder<T> newCommandBuilder(Class<T> senderTypeClass, @Nonnull Plugin plugin, @Nonnull String name) {
		return new SimpleCommandBuilder<T>(plugin, name, senderTypeClass);
	}
	
	public static <T extends CommandSender> CommandBuilder<T> newCommandBuilder(Class<T> senderTypeClass, @Nonnull Plugin plugin, @Nonnull String holder, @Nonnull String name) {
		return new SimpleCommandBuilder<T>(plugin, holder, name);
	}
	
	public static <T extends CommandSender> CommandArgumentBuilder<T> newArgumentBuilder(Class<T> senderTypeClass, @Nonnull String name) {
		return new SimpleCommandArgumentBuilder<T>(name, null);
	}
	
	private Commands() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}