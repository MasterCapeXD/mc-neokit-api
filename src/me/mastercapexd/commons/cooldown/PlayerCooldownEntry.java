package me.mastercapexd.commons.cooldown;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

public class PlayerCooldownEntry extends CooldownEntry<Player> {

	public PlayerCooldownEntry(@Nonnull String id, @Nonnull Player player) {
		super(id, player);
	}
}