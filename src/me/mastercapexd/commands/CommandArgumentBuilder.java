package me.mastercapexd.commands;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

public interface CommandArgumentBuilder<S extends CommandSender> extends CommandElementBuilder<CommandArgumentBuilder<S>, CommandArgument<S>, S> {

	CommandArgumentBuilder<S> addChild(@Nonnull CommandArgumentBuilder<? extends CommandSender> child);
}