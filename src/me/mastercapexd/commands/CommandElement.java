package me.mastercapexd.commands;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;

public interface CommandElement<S extends CommandSender> {

	@Nonnull
	String getName();
	
	@Nonnull
	String[] aliases();
	
	@Nullable
	String getDescription();
	
	@Nullable
	String getPermission();
	
	Function<CommandSender, String> getPermissionMessageApplier();
	
	Function<CommandSender, String> getWrongSenderMessageApplier();
	
	@Nonnull
	BiConsumer<S, String[]> getExecutor();
	
	@Nonnull
	BiFunction<CommandSender, String[], List<String>> getTabCompleter();
}