package me.mastercapexd.commands;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;

public interface CommandElement {

	@Nonnull
	String getName();
	
	@Nonnull
	String[] aliases();
	
	@Nullable
	String getDescription();
	
	@Nullable
	String getPermission();
	
	@Nonnull
	BiFunction<CommandSender, String[], CommandResult> getExecutor();
	
	@Nonnull
	BiFunction<CommandSender, String[], List<String>> getTabCompleter();
	
	@Nonnull
	Map<CommandResult, Function<CommandSender, String>> getMessageMap();

	@Nonnull
	default Optional<String> getMessageOptional(@Nonnull CommandResult result, @Nonnull CommandSender sender) {
		Function<CommandSender, String> messageApplier = getMessageMap().get(result);
		if (messageApplier == null)
			return Optional.empty();
		
		return Optional.ofNullable(messageApplier.apply(sender));
	}
	
	@Nullable
	default String getMessage(@Nonnull CommandResult result, @Nonnull CommandSender sender) {
		return getMessageOptional(result, sender).get();
	}
}