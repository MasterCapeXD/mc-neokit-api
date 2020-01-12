
package me.mastercapexd.commands;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import me.mastercapexd.commons.util.Builder;

public interface CommandElementBuilder<T extends CommandElementBuilder<T, R>, R extends CommandElement> extends Builder<R> {

	@Nonnull
	T registerAlias(@Nonnull String alias);
	
	@Nonnull
	T withDescription(@Nonnull String description);
	
	@Nonnull
	T withPermission(@Nonnull String permission);
	
	@Nonnull
	T withExecutor(@Nonnull BiFunction<CommandSender, String[], CommandResult> biFunction);
	
	@Nonnull
	T withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction);
	
	@Nonnull
	T withMessage(@Nonnull CommandResult result, @Nonnull Function<CommandSender, String> messageFunction);
	
	@Nonnull
	default T registerAliases(@Nonnull String... aliases) {
		T builder = null;
		for (String alias : aliases)
			builder = registerAlias(alias);
		return builder;
	}
}