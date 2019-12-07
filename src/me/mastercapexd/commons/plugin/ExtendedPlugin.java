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

package me.mastercapexd.commons.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.mastercapexd.commons.terminable.TerminableConsumer;

public interface ExtendedPlugin extends Plugin, TerminableConsumer {

	@Nonnull
	<T extends Listener> T registerListener(@Nonnull T listener);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull T command, @Nonnull String name);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull T command, @Nonnull String name, @Nonnull String... aliases);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull Plugin plugin, @Nonnull T command, @Nonnull String name, @Nonnull String... aliases);
	
	@Nonnull
	<T extends CommandExecutor> T registerExecutor(@Nonnull Plugin plugin, @Nonnull T command, @Nonnull TabCompleter completer, @Nonnull String name, @Nonnull String... aliases);
	
	boolean isPluginPresent(@Nonnull String name);
	
	@Nullable
	<T> T getPlugin(@Nonnull String name, @Nonnull Class<T> pluginClass);
}