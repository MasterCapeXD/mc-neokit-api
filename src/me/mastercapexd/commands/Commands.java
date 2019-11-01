package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.plugin.Plugin;

public final class Commands {

	public static CommandBuilder newCommandBuilder(@Nonnull Plugin plugin, @Nonnull String name) {
		return new CommandBuilder(plugin, name);
	}
	
	public static CommandBuilder newCommandBuilder(@Nonnull Plugin plugin, @Nonnull String holder, @Nonnull String name) {
		return new CommandBuilder(plugin, holder, name);
	}
	
	public static CommandArgumentBuilder newArgumentBuilder(@Nonnull String name) {
		return new CommandArgumentBuilder(name, null);
	}
	
	private Commands() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}