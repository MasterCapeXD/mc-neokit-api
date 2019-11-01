package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

public class CommandBuilder implements CommandElementBuilder<CommandBuilder, Command> {

	private final Plugin plugin;
	private final CommandBaseBuilder baseBuilder;
	private final String holder;
	private final Collection<CommandArgument> arguments = Lists.newArrayList();
	
	public CommandBuilder(@Nonnull Plugin plugin, @Nonnull String name) {
		this(plugin, plugin.getName().toLowerCase(), name);
	}
	
	public CommandBuilder(@Nonnull Plugin plugin, @Nonnull String holder, @Nonnull String name) {
		this.plugin = plugin;
		this.holder = holder;
		this.baseBuilder = new CommandBaseBuilder(name);
	}
	
	@Nonnull
	@Override
	public Command build() {
		return new SimpleCommand(plugin, holder, arguments, baseBuilder.name, baseBuilder.description, baseBuilder.permission, baseBuilder.executor, baseBuilder.tabCompleter, baseBuilder.messagesMap, baseBuilder.aliases.toArray(new String[baseBuilder.aliases.size()]));
	}
	
	@Nonnull
	@Override
	public CommandBuilder registerAlias(@Nonnull String alias) {
		baseBuilder.registerAlias(alias);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBuilder withDescription(@Nonnull String description) {
		baseBuilder.withDescription(description);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBuilder withPermission(@Nonnull String permission) {
		baseBuilder.withPermission(permission);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBuilder withExecutor(@Nonnull BiFunction<CommandSender, String[], CommandResult> biFunction) {
		baseBuilder.withExecutor(biFunction);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBuilder withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction) {
		baseBuilder.withTabCompleter(biFunction);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBuilder withMessage(@Nonnull CommandResult result, @Nonnull Function<CommandSender, String> messageFunction) {
		baseBuilder.withMessage(result, messageFunction);
		return this;
	}
	
	@Nonnull
	public CommandBuilder addArgument(@Nonnull CommandArgument argument) {
		arguments.add(argument);
		return this;
	}
}