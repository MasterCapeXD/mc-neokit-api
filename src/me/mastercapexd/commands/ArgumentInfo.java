package me.mastercapexd.commands;

import javax.annotation.Nullable;

public interface ArgumentInfo extends CommandElement {

	@Nullable
	CommandElement getParent();
}