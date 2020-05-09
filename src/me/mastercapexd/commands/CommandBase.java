package me.mastercapexd.commands;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;

public class CommandBase<S extends CommandSender> implements CommandElement<S> {

	private final String name, description, permission;
	private final String[] aliases;
	private final BiConsumer<S, String[]> executor;
	private final BiFunction<CommandSender, String[], List<String>> tabCompleter;
	private final Function<CommandSender, String> permissionMessageApplier, wrongSenderMessageApplier;
	
	protected CommandBase(@Nonnull String name, @Nonnull String description, @Nonnull String permission,
			@Nonnull Function<CommandSender, String> permissionMessageApplier, @Nonnull Function<CommandSender, String> wrongSenderMessageApplier,
			@Nonnull BiConsumer<S, String[]> executor, @Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter, @Nonnull String... aliases) {
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.permissionMessageApplier = permissionMessageApplier;
		this.wrongSenderMessageApplier = wrongSenderMessageApplier;
		this.executor = executor;
		this.tabCompleter = tabCompleter;
		this.aliases = aliases;
	}
	
	@Nonnull
	public String getName() {
		return name;
	}
	
	@Nonnull
	public String[] aliases() {
		return aliases;
	}
	
	@Nonnull
	public String getDescription() {
		return description;
	}
	
	@Nullable
	public String getPermission() {
		return permission;
	}
	
	@Override
	public Function<CommandSender, String> getPermissionMessageApplier() {
		return permissionMessageApplier;
	}
	
	@Override
	public Function<CommandSender, String> getWrongSenderMessageApplier() {
		return wrongSenderMessageApplier;
	}
	
	@Nonnull
	public BiConsumer<S, String[]> getExecutor() {
		return executor;
	}
	
	@Nonnull
	public BiFunction<CommandSender, String[], List<String>> getTabCompleter() {
		return tabCompleter;
	}
}