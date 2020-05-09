package me.mastercapexd.commands;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import me.mastercapexd.commons.util.Builder;

public interface CommandElementBuilder<B extends CommandElementBuilder<B, R, S>, R extends CommandElement<S>, S extends CommandSender> extends Builder<R> {

	@Nonnull
	B registerAlias(@Nonnull String alias);
	
	@Nonnull
	B withDescription(@Nonnull String description);
	
	@Nonnull
	B withPermission(@Nonnull String permission, @Nonnull Function<CommandSender, String> messageFunction);
	
	@Nonnull
	B setWrongSenderMessage(@Nonnull Function<CommandSender, String> messageFunction);
	
	@Nonnull
	B withExecutor(@Nonnull BiConsumer<S, String[]> biConsumer);
	
	@Nonnull
	B withTabCompleter(@Nonnull BiFunction<CommandSender, String[], List<String>> biFunction);
	
	@Nonnull
	default B registerAliases(@Nonnull String... aliases) {
		B builder = null;
		for (String alias : aliases)
			builder = registerAlias(alias);
		return builder;
	}
	
	@Nonnull
	default B withPermission(@Nonnull String permission, @Nonnull String message) {
		return withPermission(permission, s -> message);
	}
	
	@Nonnull
	default B setWrongSenderMessage(@Nonnull String message) {
		return setWrongSenderMessage(s -> message);
	}
}