package me.mastercapexd.counters;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public final class CounterConsumers {

	private CounterConsumers() {}
	
	public static final BiConsumer<Player, PlayerCounter> XP_LEVEL = (player, counter) -> player.setLevel((int) Math.abs(counter.getCurrentTime()));
	
	public static final BiConsumer<Player, PlayerCounter> XP_PROGRESS = (player, counter) -> {
		final boolean isStartGreater = counter.getStartTime() > counter.getEndTime();
		long max = (isStartGreater ? counter.getStartTime() : counter.getEndTime());
		player.setExp((float) (counter.getCurrentTime() / max));
	};
	
	public static BiConsumer<Player, PlayerCounter> sound(final Sound sound) {
		return sound(sound, 1F, 1F);
	}

	public static BiConsumer<Player, PlayerCounter> sound(final Sound sound, final float volume, final float pitch) {
		return (player, counter) -> player.playSound(player.getLocation(), sound, volume, pitch);
	}
	
	public static Consumer<Counter> bossBar(final BossBar bossBar, final double max) {
		return counter -> bossBar.setProgress(counter.getCurrentTime() / max);
	}
}