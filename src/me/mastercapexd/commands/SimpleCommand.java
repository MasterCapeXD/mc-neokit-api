package me.mastercapexd.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import me.mastercapexd.commons.plugin.PluginCommand;

public class SimpleCommand extends CommandBase implements Command {

	private final PluginCommand command;
	private final String holder;
	private final Multimap<Integer, ArgumentInfo> argumentMap = HashMultimap.create();
	
	protected SimpleCommand(@Nonnull Plugin plugin, @Nonnull String holder, @Nonnull Collection<CommandArgument> argumentMap, @Nonnull String name, @Nonnull String description, @Nonnull String permission,
			@Nonnull BiFunction<CommandSender, String[], CommandResult> executor,
			@Nonnull BiFunction<CommandSender, String[], List<String>> tabCompleter,
			@Nonnull Map<CommandResult, Function<CommandSender, String>> messagesMap, @Nonnull String[] aliases) {
		super(name, description, permission, executor, tabCompleter, messagesMap, aliases);
		this.holder = holder;
		this.load(argumentMap, this, 0);
		this.command = new PluginCommand(plugin, this, this, holder, name, description, "", Lists.newArrayList(aliases));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] params) {
		ArgumentInfo argument = null;
		
		for (int paramIndex = params.length - 1; paramIndex >= 0; paramIndex--) {
			final int localIndex = paramIndex;
			Collection<ArgumentInfo> args = argumentMap.get(paramIndex);
			
			Optional<ArgumentInfo> argumentOptional = args.stream().filter
					(arg -> arg.getName().equalsIgnoreCase(params[localIndex]) || ArrayUtils.contains(arg.aliases(), params[localIndex]))
					.findFirst();
			
			if (argumentOptional.isPresent() && 
					(params.length <= 1 || argumentOptional.get().getParent().getName().equalsIgnoreCase(params[localIndex - 1]) ||
										ArrayUtils.contains(argumentOptional.get().getParent().aliases(), params[localIndex - 1]))) {
				argument = argumentOptional.get();
				break;
			}
		}
		
		if (argument == null) {
			if (getPermission() != null && !sender.hasPermission(getPermission()))
				getMessageOptional(CommandResult.NO_PERMISSION, sender).ifPresent(msg -> sender.sendMessage(msg));
			else {
				CommandResult result = getExecutor().apply(sender, params);
				getMessageOptional(result, sender).ifPresent(msg -> sender.sendMessage(msg));
			}
			return true;
		}
		
		if (argument.getPermission() != null && !sender.hasPermission(argument.getPermission())) {
			argument.getMessageOptional(CommandResult.NO_PERMISSION, sender).ifPresent(msg -> sender.sendMessage(msg));
			return true;
		}
		
		CommandResult result = argument.getExecutor().apply(sender, params);
		argument.getMessageOptional(result, sender).ifPresent(msg -> sender.sendMessage(msg));
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] params) {
		if (!getName().equalsIgnoreCase(command.getName()) && !ArrayUtils.contains(aliases(), command.getName()))
			return Collections.emptyList();
		else {
			final int paramIndex = params.length - 1;
			Collection<ArgumentInfo> args = argumentMap.get(paramIndex);
			
			if (args.isEmpty())
				return Collections.emptyList();
			
			Optional<ArgumentInfo> argumentOptional = args.stream().filter
					(arg -> arg.getName().equalsIgnoreCase(params[paramIndex]) || ArrayUtils.contains(arg.aliases(), params[paramIndex]))
					.findFirst();
			
			if (!argumentOptional.isPresent()) {
				List<String> completions = Lists.newArrayList();
				args.stream().filter(arg -> getName().startsWith(params[paramIndex]) && 
						(params.length <= 1 || arg.getParent().getName().equalsIgnoreCase(params[paramIndex - 1]) ||
						ArrayUtils.contains(arg.getParent().aliases(), params[paramIndex - 1])))
				.forEach(arg -> completions.add(arg.getName()));
				return completions;
			}
			
			ArgumentInfo argument = argumentOptional.get();
			if (params.length <= 1
					|| argument.getParent().getName().equalsIgnoreCase(params[paramIndex - 1])
					|| ArrayUtils.contains(argument.getParent().aliases(), params[paramIndex - 1]))
				return argument.getTabCompleter().apply(sender, params);
			return Collections.emptyList();
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
	
	private void load(Collection<CommandArgument> arguments, CommandElement parent, int index) {
		for (CommandArgument argument : arguments) {
			SimpleArgumentInfo argumentInfo = new SimpleArgumentInfo(argument, parent);
			argumentMap.put(index, argumentInfo);
			
			if (argument.getChilds().isEmpty())
				continue;
			
			load(argument.getChilds(), argumentInfo, index + 1);
		}
	}
}