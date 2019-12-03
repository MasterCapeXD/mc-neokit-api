package me.mastercapexd.counters;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class AsyncBukkitTaskCounterProcessor extends AbstractCounterProcessor {

	private final Plugin plugin;
	private final long period;
	private BukkitTask task;
	
	public AsyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin) {
		this(plugin, 20L);
	}
	
	public AsyncBukkitTaskCounterProcessor(@Nonnull Plugin plugin, long period) {
		this.plugin = plugin;
		this.period = period;
	}
	
	@Override
	public void setProcessing(boolean processing) {
		if (processing)
			this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> handle(), period, period);
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