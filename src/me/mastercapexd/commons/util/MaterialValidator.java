package me.mastercapexd.commons.util;

import javax.annotation.Nonnull;

import org.bukkit.Material;

public class MaterialValidator {

	public static boolean isBlock(@Nonnull Material material) {
		return material.isBlock();
	}
	
	public static boolean isBurnable(@Nonnull Material material) {
		return material.isBurnable();
	}
	
	public static boolean isEdible(@Nonnull Material material) {
		return material.isEdible();
	}
	
	public static boolean isFlammable(@Nonnull Material material) {
		return material.isFlammable();
	}
	
	public static boolean isFuel(@Nonnull Material material) {
		return material.isFuel();
	}
	
	public static boolean isOccluding(@Nonnull Material material) {
		return material.isOccluding();
	}
	
	public static boolean isRecord(@Nonnull Material material) {
		return material.isRecord();
	}
	
	public static boolean isSolid(@Nonnull Material material) {
		return material.isSolid();
	}
	
	@Deprecated
	public static boolean isTransparent(@Nonnull Material material) {
		return material.isTransparent();
	}
	
	public static boolean isArmor(@Nonnull Material material) {
		return isHelmetMaterial(material) || isChestplateMaterial(material) ||
				isLeggingsMaterial(material) || isBootsMaterial(material);
	}
	
	public static boolean isHelmetMaterial(@Nonnull Material material) {
		return material.name().endsWith("HELMET");
	}
	
	public static boolean isChestplateMaterial(@Nonnull Material material) {
		return material.name().endsWith("CHESTPLATE");
	}
	
	public static boolean isLeggingsMaterial(@Nonnull Material material) {
		return material.name().endsWith("LEGGINGS");
	}
	
	public static boolean isBootsMaterial(@Nonnull Material material) {
		return material.name().endsWith("BOOTS");
	}
	
	public static boolean isSword(@Nonnull Material material) {
		return material.name().endsWith("SWORD");
	}
	
	public static boolean isAxe(@Nonnull Material material) {
		return material.name().endsWith("AXE");
	}
	
	public static boolean isPickaxe(@Nonnull Material material) {
		return material.name().endsWith("PICKAXE");
	}
	
	public static boolean isHoe(@Nonnull Material material) {
		return material.name().endsWith("HOE");
	}
	
	public static boolean isShovel(@Nonnull Material material) {
		String name = material.name();
		return name.endsWith("SHOVEL") ||
				name.endsWith("SPADE");
	}
	
	public static boolean isContainer(@Nonnull Material material) {
		String name = material.name();
		return name.endsWith("CHEST")
				||
				material.name().endsWith("SHULKER_BOX")
				||
				material.name().endsWith("FURANCE")
				||
				material.name().endsWith("DROPPER")
				||
				material.name().endsWith("DISPENSER")
				||
				material.name().endsWith("HOPPER");
	}
}