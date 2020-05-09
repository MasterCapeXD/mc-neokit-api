package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public interface Command<S extends CommandSender> extends CommandElement<S>, CommandExecutor, TabCompleter {

	@Nonnull
	String getCommandHolder();
	
	void register();
}