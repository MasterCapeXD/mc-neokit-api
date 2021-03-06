/*
 * This file is part of helper, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.mastercapexd.commons;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

public final class Players {

	private Players() { throw new UnsupportedOperationException("This class cannot be instantiated!"); }
	
	@Nullable
	public static Player getNullable(String name) {
		return Bukkit.getPlayer(name);
	}
	
	@Nullable
	public static Player getNullable(UUID uuid) {
		return Bukkit.getPlayer(uuid);
	}
	
	public static Optional<Player> get(String name) {
		return Optional.ofNullable(getNullable(name));
	}
	
	public static Optional<Player> get(UUID uuid) {
		return Optional.ofNullable(getNullable(uuid));
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Player> online() {
		return (Collection<Player>) Bukkit.getOnlinePlayers();
	}
	
	public static Stream<Player> stream() {
		return online().stream();
	}
	
	public static Stream<Player> streamRadius(Location center, int radius) {
		return center.getWorld().getNearbyEntities(center, radius, radius, radius).stream()
				.filter(e -> e instanceof Player)
				.map(e -> ((Player) e));
	}
	
	public static void forEach(Consumer<Player> consumer) {
		online().forEach(consumer);
	}
	
	public static void forEachInRadius(Location center, int radius, Consumer<Player> consumer) {
		center.getWorld().getNearbyEntities(center, radius, radius, radius).stream()
		.filter(e -> e instanceof Player)
		.map(e -> ((Player) e))
		.forEach(consumer);
	}
	
	public static void filteredForEach(Predicate<Player> predicate, Consumer<Player> consumer) {
		stream().filter(predicate).forEach(consumer);
	}
	
	public static void broadcast(String... messages) {
		Arrays.stream(messages).forEach(msg -> Bukkit.broadcastMessage(msg));
	}
	
	public static void broadcast(String permission, String... messages) {
		Arrays.stream(messages).forEach(msg -> Bukkit.broadcast(msg, permission));
	}
	
	public static void sendMessages(Predicate<Player> predicate, String... messages) {
		filteredForEach(predicate, player -> player.sendMessage(messages));
	}
	
	public static void sendMessages(Player player, String... messages) {
		player.sendMessage(messages);
	}
	
	@SuppressWarnings("deprecation")
	@Nullable
	public static OfflinePlayer getOfflineNullable(String name) {
		return Bukkit.getOfflinePlayer(name);
	}
	
	@Nullable
	public static OfflinePlayer getOfflineNullable(UUID uuid) {
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public static Optional<OfflinePlayer> getOffline(String name) {
		return Optional.ofNullable(getOfflineNullable(name));
	}
	
	public static Optional<OfflinePlayer> getOffline(UUID uuid) {
		return Optional.ofNullable(getOfflineNullable(uuid));
	}
	
	public static Collection<OfflinePlayer> offline() {
		return Sets.newHashSet(Bukkit.getOfflinePlayers());
	}
	
	public static void resetWalkSpeed(Player player) {
		player.setWalkSpeed(0.2F);
	}
	
	public static void resetFlySpeed(Player player) {
		player.setFlySpeed(0.1F);
	}
	
	public static void playSound(Sound sound, float volume, float pitch) {
		playSound(online(), sound, volume, pitch);
	}
	
	public static void playSound(Collection<? extends Player> players, Sound sound, float volume, float pitch) {
		for (Player player : players)
			player.playSound(player.getLocation(), sound, volume, pitch);
	}
}