package me.mastercapexd.commands;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

public class CommandBase implements CommandElement {

	private final String name, description, permission;
	private final String[] aliases;
	private final BiFunction<CommandSender, String[], CommandResult> executor;
	private final BiFunction<CommandSender, String[], List<String>> tabCompleter;
	private final Map<CommandResult, Function<CommandSender, String>> resultMessages;
	
	protected CommandBase(@Nonnull String name, @Nonnull String description, @Nonnull String permission,
			@Nonnull BiFunction<CommandSender, String[], CommandResult> executor, @Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter, @Nonnull Map<CommandResult, Function<CommandSender, String>> messagesMap, @Nonnull String... aliases) {
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.executor = executor;
		this.tabCompleter = tabCompleter;
		this.resultMessages = ImmutableMap.copyOf(messagesMap);
		this.aliases = aliases;
	}
	
	@Nonnull
	public String getName() {
		return name;
	}
	
	@Nonnull
	public String[] aliases() {
		return aliases;
	}
	
	@Nonnull
	public String getDescription() {
		return description;
	}
	
	@Nullable
	public String getPermission() {
		return permission;
	}
	
	@Nonnull
	public BiFunction<CommandSender, String[], CommandResult> getExecutor() {
		return executor;
	}
	
	@Nonnull
	public BiFunction<CommandSender, String[], List<String>> getTabCompleter() {
		return tabCompleter;
	}
	
	@Nonnull
	@Override
	public Map<CommandResult, Function<CommandSender, String>> getMessageMap() {
		return ImmutableMap.copyOf(resultMessages);
	}
}