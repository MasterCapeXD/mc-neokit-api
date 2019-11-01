package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableList;

public class SimpleCommandArgument extends SimpleArgumentInfo implements CommandArgument {

	private final Collection<CommandArgument> childs;
	
	protected SimpleCommandArgument(@Nonnull String name, @Nonnull String description, @Nonnull String permission,
			@Nonnull BiFunction<CommandSender, String[], CommandResult> executor,
			@Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter,
			@Nonnull Map<CommandResult, Function<CommandSender, String>> messagesMap, @Nonnull String[] aliases, CommandElement parent, @Nonnull Collection<CommandArgument> childs) {
		super(name, description, permission, executor, tabCompleter, messagesMap, aliases, parent);
		this.childs = ImmutableList.copyOf(childs);
	}
	
	@Nonnull
	@Override
	public Collection<CommandArgument> getChilds() {
		return childs;
	}
}