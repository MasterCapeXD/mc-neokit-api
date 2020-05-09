package me.mastercapexd.commands;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

public interface CommandArgument<S extends CommandSender> extends ArgumentInfo<S> {

	@Nonnull
	Collection<CommandArgument<S>> getChilds();
}