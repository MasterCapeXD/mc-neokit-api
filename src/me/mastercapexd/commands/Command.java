package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public interface Command extends CommandElement, CommandExecutor, TabCompleter {

	@Nonnull
	String getCommandHolder();
	
	void register();
}