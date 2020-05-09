package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

public interface CommandBuilder<S extends CommandSender> extends CommandElementBuilder<CommandBuilder<S>, Command<S>, S> {

	CommandBuilder<S> addArgument(@Nonnull CommandArgument<CommandSender> argument);
}