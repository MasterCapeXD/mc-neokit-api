package me.mastercapexd.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Sets;

public class CommandBaseBuilder<S extends CommandSender> implements CommandElementBuilder<CommandBaseBuilder<S>, CommandBase<S>, S> {

	protected final String name;
	protected final Collection<String> aliases = Sets.newHashSet();
	protected String description = "", permission;
	protected BiConsumer<S, String[]> executor = (sender, params) -> {};
	protected BiFunction<CommandSender, String[], List<String>> tabCompleter = (sender, params) -> Collections.emptyList();
	protected Function<CommandSender, String> noPermissionMessage = s -> "You don't have permission to use this command!";
	protected Function<CommandSender, String> wrongSenderMessage = s -> "Access denied!";
	
	public CommandBaseBuilder(@Nonnull String name) {
		this.name = name;
	}
	
	@Nonnull
	@Override
	public CommandBase<S> build() {
		return new CommandBase<S>(name, description, permission, noPermissionMessage, wrongSenderMessage, executor, tabCompleter, aliases.toArray(new String[aliases.size()]));
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder<S> registerAlias(@Nonnull String alias) {
		aliases.add(alias);
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder<S> withDescription(@Nonnull String description) {
		this.description = description;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder<S> withPermission(@Nonnull String permission, @Nonnull Function<CommandSender, String> messageFunction) {
		this.permission = permission;
		this.noPermissionMessage = messageFunction;
		return this;
	}
	
	@Override
	public CommandBaseBuilder<S> setWrongSenderMessage(@Nonnull Function<CommandSender, String> messageFunction) {
		this.wrongSenderMessage = messageFunction;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder<S> withExecutor(@Nonnull BiConsumer<S, String[]> biConsumer) {
		this.executor = biConsumer;
		return this;
	}
	
	@Nonnull
	@Override
	public CommandBaseBuilder<S> withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction) {
		this.tabCompleter = biFunction;
		return this;
	}
}