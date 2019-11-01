package me.mastercapexd.commons.cooldown;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

public class StringCooldownEntry extends CooldownEntry<String> {

	public StringCooldownEntry(@Nonnull String id, @Nonnull Player player) {
		this(id, player.getName());
	}
	
	public StringCooldownEntry(@Nonnull String id, @Nonnull String owner) {
		super(id, owner);
	}
}