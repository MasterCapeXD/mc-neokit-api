package me.mastercapexd.commons.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.bukkit.Bukkit;

public final class DataGetter {

	private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();
	
	public static <T> T getDataByMethodHandle(Object obj, String methodName, Class<T> returnType) throws Throwable {
		return (T) getMethodHandle(obj.getClass(), methodName, returnType).invoke(obj);
	}
	
	public static MethodHandle getMethodHandle(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... objects) throws Throwable {
		return LOOKUP.findVirtual(clazz, methodName, MethodType.methodType(returnType, objects));
	}
	
	public static MethodHandle getConstructor(Class<?> clazz, Class<?>... objects) throws Throwable {
		return LOOKUP.findConstructor(clazz, MethodType.methodType(void.class, objects));
	}
	
	public static <T> T createInstance(Class<T> clazz) throws Throwable {
		return (T) getConstructor(clazz).invoke();
	}
	
	public static String getBukkitVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}
	
	public static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getBukkitVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Class<?> getCraftBukkitClass(String name) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getBukkitVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T findGetter(Object object, Class<T> fieldClass, String fieldName) throws Throwable {
		return (T) LOOKUP.findGetter(object.getClass(), fieldName, fieldClass).invoke(object);
	}
}