package me.mastercapexd.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CommandBaseBuilder implements CommandElementBuilder<CommandBaseBuilder, CommandBase> {

	protected final String name;
	protected final Collection<String> aliases = Sets.newHashSet();
	protected final Map<CommandResult, Function<CommandSender, String>> messagesMap = Maps.newHashMap();
	protected String description = "", permission;
	protected BiFunction<CommandSender, String[], CommandResult> executor = (sender, params) -> CommandResult.SUCCESS;
	protected BiFunction<CommandSender, String[], List<String>> tabCompleter = (sender, params) -> Collections.emptyList();
	
	public CommandBaseBuilder(@Nonnull String name) {
		this.name = name;
	}
	
	@Nonnull
	@Override
	public CommandBase build() {
		return new CommandBase(name, description, permission, executor, tabCompleter, messagesMap, aliases.toArray(new String[aliases.size()]));
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder registerAlias(@Nonnull String alias) {
		aliases.add(alias);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder withDescription(@Nonnull String description) {
		this.description = description;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder withPermission(@Nonnull String permission) {
		this.permission = permission;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder withExecutor(@Nonnull BiFunction<CommandSender, String[], CommandResult> biFunction) {
		this.executor = biFunction;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction) {
		this.tabCompleter = biFunction;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder withMessage(@Nonnull CommandResult result, @Nonnull Function<CommandSender, String> messageFunction) {
		messagesMap.put(result, messageFunction);
		return this;
	}
}