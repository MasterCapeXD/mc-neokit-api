package me.mastercapexd.counters;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class SyncBukkitTaskCounterProcessor extends AbstractCounterProcessor {

	private final Plugin plugin;
	private final long period;
	private BukkitTask task;
	
	public SyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin) {
		this(plugin, 20L);
	}
	
	public SyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin, long period) {
		this.plugin = plugin;
		this.period = period;
	}
	
	@Override
	public void setProcessing(boolean processing) {
		if (processing)
			this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> handle(), period, period);
		else {
			if (isProcessing()) {
				task.cancel();
				task = null;
			}
		}
	}

	@Override
	public boolean isProcessing() {
		return task != null;
	}
}