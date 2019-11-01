package me.mastercapexd.commands;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface CommandArgument extends ArgumentInfo {

	@Nonnull
	Collection<CommandArgument> getChilds();
}