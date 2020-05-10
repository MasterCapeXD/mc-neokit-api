package me.mastercapexd.commands;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import me.mastercapexd.commons.plugin.PluginCommand;
import me.mastercapexd.commons.util.ArrayUtils;

public class SimpleCommand<S extends CommandSender> extends CommandBase<S> implements Command<S> {

	private final PluginCommand command;
	private final String holder;
	private final Multimap<Integer, ArgumentInfo<CommandSender>> argumentMap = HashMultimap.create();
	
	protected SimpleCommand(@Nonnull Plugin plugin, @Nonnull String holder, @Nonnull Collection<CommandArgument<? extends CommandSender>> argumentMap, @Nonnull String name, @Nonnull String description, @Nonnull String permission,
			@Nonnull Function<CommandSender, String> permissionMessageFunction,
			@Nonnull Function<CommandSender, String> wrongSenderInstanceMessage,
			@Nonnull BiConsumer<S, String[]> executor,
			@Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter,
			@Nonnull String[] aliases) {
		super(name, description, permission, permissionMessageFunction, wrongSenderInstanceMessage, executor, tabCompleter, aliases);
		this.holder = holder;
		this.load(argumentMap, this, 0);
		this.command = new PluginCommand(plugin, this, this, holder, name, description, "", Lists.newArrayList(aliases));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] params) {
		ArgumentInfo<CommandSender> argument = null;
		int argIndex = 0;
		
		for (int paramIndex = params.length - 1; paramIndex >= 0; paramIndex--) {
			final int localIndex = paramIndex;
			Collection<ArgumentInfo<CommandSender>> args = argumentMap.get(paramIndex);
			
			Optional<ArgumentInfo<CommandSender>> argumentOptional = args.stream().filter
					(arg -> arg.getName().equalsIgnoreCase(params[localIndex]) || ArrayUtils.contains(arg.aliases(), params[localIndex]))
					.findFirst();
			
			if (argumentOptional.isPresent() && 
					(localIndex <= 1 || argumentOptional.get().getParent().getName().equalsIgnoreCase(params[localIndex - 1]) ||
										ArrayUtils.contains(argumentOptional.get().getParent().aliases(), params[localIndex - 1]))) {
				argument = argumentOptional.get();
				argIndex = localIndex;
				break;
			}
		}
		
		if (argument == null) {
			try {
				S s = (S) sender;
				
				if (getPermission() != null && !sender.hasPermission(getPermission())) {
					sender.sendMessage(getPermissionMessageApplier().apply(s));
					return true;
				}
				
				getExecutor().accept(s, params);
			} catch (ClassCastException exception) {
				sender.sendMessage(getWrongSenderMessageApplier().apply(sender));
				return true;
			} catch (NullPointerException ignored) {}
		}
		
		try {
			S s = (S) sender;
			
			if (getPermission() != null && !sender.hasPermission(getPermission())) {
				sender.sendMessage(argument.getWrongSenderMessageApplier().apply(sender));
				return true;
			}
			
			argument.getExecutor().accept(s, ArrayUtils.subarray(params, argIndex + 1, params.length));
		} catch (ClassCastException exception) {
			sender.sendMessage(argument.getWrongSenderMessageApplier().apply(sender));
		} catch (NullPointerException ignored) {}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] params) {
		if (!getName().equalsIgnoreCase(command.getName()) && !ArrayUtils.contains(aliases(), command.getName()))
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		else {
			final int paramIndex = params.length - 1;
			Collection<ArgumentInfo<CommandSender>> args = argumentMap.get(paramIndex);
			
			if (args.isEmpty())
				return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
			
			Optional<ArgumentInfo<CommandSender>> argumentOptional = args.stream().filter
					(arg -> getName().startsWith(params[paramIndex].toLowerCase()) || arg.getName().equalsIgnoreCase(params[paramIndex]) || ArrayUtils.contains(arg.aliases(), params[paramIndex]))
					.findFirst();
			
			if (!argumentOptional.isPresent())
				Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
			
			ArgumentInfo<CommandSender> argument = argumentOptional.get();
			List<String> result = argument.getTabCompleter().apply(sender, ArrayUtils.subarray(params, paramIndex + 1, params.length));
			if (params.length <= 1)
				return result.stream().filter(str -> str.startsWith(params[paramIndex].toLowerCase())).collect(Collectors.toList());
			else {
				result.removeIf(str -> !argument.getParent().getName().equalsIgnoreCase(params[paramIndex - 1]) ||
						!str.startsWith(params[paramIndex].toLowerCase()));
				return result;
			}
		}
	}
	
	@Nonnull
	@Override
	public String getCommandHolder() {
		return holder;
	}
	
	@Override
	public void register() {
		command.register();
	}
	
	private void load(Collection<CommandArgument<? extends CommandSender>> arguments, CommandElement<? extends CommandSender> parent, int index) {
		for (CommandArgument<? extends CommandSender> argument : arguments) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			SimpleArgumentInfo<CommandSender> argumentInfo = new SimpleArgumentInfo(argument, parent);
			argumentMap.put(index, argumentInfo);
			
			if (argument.getChilds().isEmpty())
				continue;
			
			load(argument.getChilds(), argumentInfo, index + 1);
		}
	}
}