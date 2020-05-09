package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

//@SuppressWarnings("rawtypes")
public class SimpleCommandArgumentBuilder<S extends CommandSender> implements CommandArgumentBuilder<S> {

	private final CommandBaseBuilder<S> baseBuilder;
	private final Collection<CommandArgument<? extends CommandSender>> childs = Lists.newArrayList();
	private final CommandElement<S> parent;
	
	public SimpleCommandArgumentBuilder(@Nonnull String name, CommandElement<S> parent) {
		this.baseBuilder = new CommandBaseBuilder<S>(name);
		this.parent = parent;
	}
	
	@Nonnull
	@Override
	public CommandArgument<S> build() {
		return new SimpleCommandArgument<S>(baseBuilder.name, baseBuilder.description, baseBuilder.permission, baseBuilder.noPermissionMessage, baseBuilder.wrongSenderMessage, baseBuilder.executor, baseBuilder.tabCompleter, baseBuilder.aliases.toArray(new String[baseBuilder.aliases.size()]), parent, childs);
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder<S> registerAlias(@Nonnull String alias) {
		baseBuilder.registerAlias(alias);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder<S> withDescription(@Nonnull String description) {
		baseBuilder.withDescription(description);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder<S> withPermission(@Nonnull String permission, @Nonnull Function<CommandSender, String> messageFunction) {
		baseBuilder.withPermission(permission, messageFunction);
		return this;
	}
	
	@Override
	public CommandArgumentBuilder<S> setWrongSenderMessage(Function<CommandSender, String> messageFunction) {
		baseBuilder.setWrongSenderMessage(messageFunction);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder<S> withExecutor(@Nonnull BiConsumer<S, String[]> biConsumer) {
		baseBuilder.withExecutor(biConsumer);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandArgumentBuilder<S> withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction) {
		baseBuilder.withTabCompleter(biFunction);
		return this;
	}
	
	@Nonnull
	public CommandArgumentBuilder<S> addChild(@Nonnull CommandArgumentBuilder<? extends CommandSender> child) {
		childs.add(child.build());
		return this;
	}
}