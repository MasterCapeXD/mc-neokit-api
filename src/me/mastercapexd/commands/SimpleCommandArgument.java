package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableList;

public class SimpleCommandArgument<S extends CommandSender> extends SimpleArgumentInfo<S> implements CommandArgument<S> {

	private final Collection<CommandArgument<? extends CommandSender>> childs;
	
	protected SimpleCommandArgument(@Nonnull String name, @Nonnull String description, @Nonnull String permission,
			@Nonnull Function<CommandSender, String> permissionMessageApplier, @Nonnull Function<CommandSender, String> wrongSenderMessageApplier,
			@Nonnull BiConsumer<S, String[]> executor,
			@Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter,
			@Nonnull String[] aliases, CommandElement<S> parent, @SuppressWarnings("rawtypes") @Nonnull Collection<? extends CommandArgument> childs) {
		super(name, description, permission, permissionMessageApplier, wrongSenderMessageApplier, executor, tabCompleter, aliases, parent);
		this.childs = ImmutableList.copyOf(childs);
	}
	
	@Nonnull
	@Override
	public Collection<CommandArgument<? extends CommandSender>> getChilds() {
		return childs;
	}
}