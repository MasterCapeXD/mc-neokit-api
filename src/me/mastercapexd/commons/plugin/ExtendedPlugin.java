package me.mastercapexd.commons.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.mastercapexd.commons.terminable.TerminableConsumer;

public interface ExtendedPlugin extends Plugin, TerminableConsumer {

	@Nonnull
	<T extends Listener> T registerListener(@Nonnull T listener);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull T command, @Nonnull String name);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull T command, @Nonnull String name, @Nonnull String... aliases);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull Plugin plugin, @Nonnull T command, @Nonnull String name, @Nonnull String... aliases);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull Plugin plugin, @Nonnull T command, @Nonnull TabCompleter completer, @Nonnull String name, @Nonnull String... aliases);
	
	boolean isPluginPresent(@Nonnull String name);
	
	@Nullable
	<T> T getPlugin(@Nonnull String name, @Nonnull Class<T> pluginClass);
}