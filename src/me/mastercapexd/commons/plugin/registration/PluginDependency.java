package me.mastercapexd.commons.plugin.registration;

import javax.annotation.Nonnull;

public @interface PluginDependency {

	@Nonnull
	String value();
	
	boolean soft() default false;
}