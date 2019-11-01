package me.mastercapexd.commons.plugin;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

public class PluginCommand extends org.bukkit.command.Command implements PluginIdentifiableCommand {

	private final Plugin plugin;
	private final CommandExecutor executor;
	private final TabCompleter completer;
	private final String holder;
	private boolean registered;
	
	public PluginCommand(@Nonnull Plugin plugin, @Nonnull CommandExecutor executor, @Nonnull TabCompleter completer, @Nonnull String holder, @Nonnull String name, @Nonnull String description, @Nonnull String usageMessage, @Nonnull List<String> aliases) {
		super(name, description, usageMessage, aliases);
		this.plugin = plugin;
		this.executor = executor;
		this.completer = completer;
		this.holder = holder;
	}
	
	@Nonnull
	@Override
	public Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		return executor.onCommand(sender, this, label, args);
	}
	
	@Nonnull
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return completer.onTabComplete(sender, this, alias, args);
	}
	
	public void register() {
		if (registered)
			return;
		
		try {
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
			commandMap.register(getName(), holder, this);
			this.registered = true;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}