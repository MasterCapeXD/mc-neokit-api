package me.mastercapexd.commands;

import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;

public interface ArgumentInfo<S extends CommandSender> extends CommandElement<S> {

	@Nullable
	CommandElement<S> getParent();
}