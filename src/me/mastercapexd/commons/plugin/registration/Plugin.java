package me.mastercapexd.commons.plugin.registration;

import org.bukkit.plugin.PluginLoadOrder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Plugin {

	@Nonnull
	String name();
	
	@Nonnull
	String version() default "";
	
	@Nonnull
	String description() default "";
	
	@Nonnull
	PluginLoadOrder load() default PluginLoadOrder.POSTWORLD;
	
	@Nonnull
	String[] authors() default {};
	
	@Nonnull
	String website() default "";
	
	@Nonnull
	PluginDependency[] depends() default {};
	
	@Nonnull
	String[] hardDepends() default {};
	
	@Nonnull
	String[] softDepends() default {};
	
	@Nonnull
	String[] loadBefore() default {};
}