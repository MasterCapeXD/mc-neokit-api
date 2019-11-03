package me.mastercapexd.commons.plugin;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import me.mastercapexd.commons.Ticks;
import me.mastercapexd.commons.terminable.TerminableRegistry;

public class SpigotPlugin extends JavaPlugin implements ExtendedPlugin {

	private TerminableRegistry terminableRegistry;
	
	protected void onPluginLoad() {}
	
	protected void onPluginEnable() {}
	
	protected void onPluginDisable() {}
	
	@Override
	public void onLoad() {
		this.terminableRegistry = TerminableRegistry.create();
		onPluginLoad();
	}
	
	@Override
	public void onEnable() {
		getServer().getScheduler().runTaskTimerAsynchronously(this, this.terminableRegistry::cleanup,
				Ticks.from(10, TimeUnit.SECONDS),
				Ticks.from(30, TimeUnit.SECONDS));
		
		onPluginEnable();
	}
	
	@Override
	public void onDisable() {
		onPluginDisable();
		terminableRegistry.terminateSilently();
	}
	
	@Override
	public <T extends AutoCloseable> T bind(T terminable) {
		return this.terminableRegistry.bind(terminable);
	}
	
	@Override
	public <T extends Listener> T registerListener(T listener) {
		getServer().getPluginManager().registerEvents(listener, this);
		return listener;
	}
	
	@Override
	public <T extends CommandExecutor> T registerExecutor(T command, String name) {
		return registerExecutor(command, name, new String[]{});
	}
	
	@Override
	public <T extends CommandExecutor> T registerExecutor(T command, String name, String... aliases) {
		return registerExecutor(this, command, name, aliases);
	}
	
	@Override
	public <T extends CommandExecutor> T registerExecutor(Plugin plugin, T command, String name, String... aliases) {
		return registerExecutor(plugin, command,
				(sender, cmd, label, args) -> Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList()), name, aliases);
	}
	
	@Override
	public <T extends CommandExecutor> T registerExecutor(Plugin plugin, T command, TabCompleter completer, String name,
			String... aliases) {
		PluginCommand pluginCommand = new PluginCommand(plugin, command, completer, plugin.getName().toLowerCase(), name, "", ChatColor.RED + "Wrong usage!", Lists.newArrayList(aliases));
		pluginCommand.register();
		return command;
	}
	
	@Override
	public boolean isPluginPresent(String name) {
		return getServer().getPluginManager().getPlugin(name) != null;
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getPlugin(String name, Class<T> pluginClass) {
		return (T) getServer().getPluginManager().getPlugin(name);
	}
}