package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

public class SimpleCommandBuilder<S extends CommandSender> implements CommandBuilder<S> {

	private final Plugin plugin;
	private final CommandBaseBuilder<S> baseBuilder;
	private final String holder;
	private final Collection<CommandArgument<? extends CommandSender>> arguments = Lists.newArrayList();
	
	public SimpleCommandBuilder(@Nonnull Plugin plugin, @Nonnull String name) {
		this(plugin, plugin.getName().toLowerCase(), name);
	}
	
	public SimpleCommandBuilder(@Nonnull Plugin plugin, @Nonnull String holder, @Nonnull String name) {
		this.plugin = plugin;
		this.holder = holder;
		this.baseBuilder = new CommandBaseBuilder<S>(name);
	}
	
	@Nonnull
	@Override
	public Command<S> build() {
		return new SimpleCommand<S>(plugin, holder, arguments, baseBuilder.name, baseBuilder.description, baseBuilder.permission, baseBuilder.noPermissionMessage, baseBuilder.wrongSenderMessage, baseBuilder.executor, baseBuilder.tabCompleter, baseBuilder.aliases.toArray(new String[baseBuilder.aliases.size()]));
	}
	
	@Nonnull
	@Override
	public SimpleCommandBuilder<S> registerAlias(@Nonnull String alias) {
		baseBuilder.registerAlias(alias);
		return this;
	}
	
	@Nonnull
	@Override
	public SimpleCommandBuilder<S> withDescription(@Nonnull String description) {
		baseBuilder.withDescription(description);
		return this;
	}
	
	@Nonnull
	@Override
	public SimpleCommandBuilder<S> withPermission(@Nonnull String permission, @Nonnull Function<CommandSender, String> messageFunction) {
		baseBuilder.withPermission(permission, messageFunction);
		return this;
	}
	
	@Override
	public SimpleCommandBuilder<S> setWrongSenderMessage(Function<CommandSender, String> messageFunction) {
		baseBuilder.setWrongSenderMessage(messageFunction);
		return this;
	}
	
	@Nonnull
	@Override
	public SimpleCommandBuilder<S> withExecutor(@Nonnull BiConsumer<S, String[]> biConsumer) {
		baseBuilder.withExecutor(biConsumer);
		return this;
	}
	
	@Nonnull
	@Override
	public SimpleCommandBuilder<S> withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction) {
		baseBuilder.withTabCompleter(biFunction);
		return this;
	}
	
	@Override
	public CommandBuilder<S> addArgument(CommandArgumentBuilder<? extends CommandSender> argumentBuilder) {
		arguments.add(argumentBuilder.build());
		return this;
	}
}