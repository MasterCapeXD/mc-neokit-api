package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

public class CommandArgumentBuilder implements CommandElementBuilder<CommandArgumentBuilder, CommandArgument>{

	private final CommandBaseBuilder baseBuilder;
	private final Collection<CommandArgument> childs = Lists.newArrayList();
	private final CommandElement parent;
	
	public CommandArgumentBuilder(@Nonnull String name, CommandElement parent) {
		this.baseBuilder = new CommandBaseBuilder(name);
		this.parent = parent;
	}
	
	@Nonnull
	@Override
	public CommandArgument build() {
		return new SimpleCommandArgument(baseBuilder.name, baseBuilder.description, baseBuilder.permission, baseBuilder.executor, baseBuilder.tabCompleter, baseBuilder.messagesMap, baseBuilder.aliases.toArray(new String[baseBuilder.aliases.size()]), parent, childs);
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder registerAlias(@Nonnull String alias) {
		baseBuilder.registerAlias(alias);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder withDescription(@Nonnull String description) {
		baseBuilder.withDescription(description);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder withPermission(@Nonnull String permission) {
		baseBuilder.withPermission(permission);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder withExecutor(@Nonnull BiFunction<CommandSender, String[], CommandResult> biFunction) {
		baseBuilder.withExecutor(biFunction);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction) {
		baseBuilder.withTabCompleter(biFunction);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder withMessage(@Nonnull CommandResult result, @Nonnull Function<CommandSender, String> messageFunction) {
		baseBuilder.withMessage(result, messageFunction);
		return this;
	}
	
	@Nonnull
	public CommandArgumentBuilder addChild(@Nonnull CommandArgumentBuilder child) {
		childs.add(child.build());
		return this;
	}
}