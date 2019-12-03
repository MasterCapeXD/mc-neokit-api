package me.mastercapexd.counters;

import java.util.Collection;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

public class PlayerTickingCounter<T extends PlayerCounter> extends TickingCounter<T> implements PlayerCounter {

	private final Collection<BiConsumer<? super Player, ? super T>> ticksConsumers;
	
	@SafeVarargs
	public PlayerTickingCounter(@Nonnull T counter, @Nonnull BiConsumer<? super Player, ? super T>... ticksConsumers) {
		super(counter);
		this.ticksConsumers = Sets.newHashSet(ticksConsumers);
	}
	
	@Override
	protected void onTick() {
		for (BiConsumer<? super Player, ? super T> consumer : ticksConsumers)
			for (Player player : getCounter().getPlayers())
				consumer.accept(player, getCounter());
	}
	
	@Nonnull
	@Override
	public Collection<Player> getPlayers() {
		return getCounter().getPlayers();
	}
	
	@Override
	public void addPlayer(Player player) {
		getCounter().addPlayer(player);
	}
	
	@Override
	public void removePlayer(Player player) {
		getCounter().removePlayer(player);
	}
}