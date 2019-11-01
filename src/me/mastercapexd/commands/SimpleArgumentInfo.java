package me.mastercapexd.commands;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

public class SimpleArgumentInfo extends CommandBase implements ArgumentInfo {

	private final CommandElement parent;
	
	protected SimpleArgumentInfo(@Nonnull ArgumentInfo info) {
		this(info, info.getParent());
	}
	
	protected SimpleArgumentInfo(@Nonnull ArgumentInfo info, CommandElement parent) {
		this(info.getName(), info.getDescription(), info.getPermission(), info.getExecutor(), info.getTabCompleter(), info.getMessageMap(), info.aliases(), parent);
	}
	
	protected SimpleArgumentInfo(@Nonnull String name, @Nonnull String description, String permission,
			@Nonnull BiFunction<CommandSender, String[], CommandResult> executor,
			@Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter,
			@Nonnull Map<CommandResult, Function<CommandSender, String>> messagesMap, @Nonnull String[] aliases, CommandElement parent) {
		super(name, description, permission, executor, tabCompleter, messagesMap, aliases);
		this.parent = parent;
	}
	
	@Override
	public CommandElement getParent() {
		return parent;
	}
}