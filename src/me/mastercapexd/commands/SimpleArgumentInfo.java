package me.mastercapexd.commands;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

public class SimpleArgumentInfo<S extends CommandSender> extends CommandBase<S> implements ArgumentInfo<S> {

	private final CommandElement<S> parent;
	
	protected SimpleArgumentInfo(@Nonnull ArgumentInfo<S> info) {
		this(info, info.getParent());
	}
	
	protected SimpleArgumentInfo(@Nonnull ArgumentInfo<S> info, CommandElement<S> parent) {
		this(info.getName(), info.getDescription(), info.getPermission(), info.getPermissionMessageApplier(), info.getWrongSenderMessageApplier(), info.getExecutor(), info.getTabCompleter(), info.aliases(), parent);
	}
	
	protected SimpleArgumentInfo(@Nonnull String name, @Nonnull String description, String permission,
			@Nonnull Function<CommandSender, String> permissionMessageApplier, @Nonnull Function<CommandSender, String> wrongSenderMessageApplier,
			@Nonnull BiConsumer<S, String[]> executor,
			@Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter,
			@Nonnull String[] aliases, CommandElement<S> parent) {
		super(name, description, permission, permissionMessageApplier, wrongSenderMessageApplier, executor, tabCompleter, aliases);
		this.parent = parent;
	}
	
	@Override
	public CommandElement<S> getParent() {
		return parent;
	}
}