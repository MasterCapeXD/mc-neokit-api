package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mastercapexd.commons.util.NumberValidator;

public final class DatableMaterial {

	public static DatableMaterial fromString(@Nonnull String str) {
		return new DatableMaterial(str); 
	}
	
	public static String toString(@Nonnull DatableMaterial datableMaterial) {
		return datableMaterial.toString();
	}
	
	private final Material material;
	private final short data;
	
	public DatableMaterial(@Nonnull Material material, short data) {
		this.material = material;
		this.data = data;
	}
	
	@SuppressWarnings("deprecation")
	public DatableMaterial(int id, short data) {
		this(Material.getMaterial(id), data);
	}
	
	@SuppressWarnings("deprecation")
	public DatableMaterial(String str) {
		String[] idData = str.split(":");
		Material material = Material.AIR;
		short data = 0;
		
		if (!NumberValidator.isInteger(idData[0])) {
			Material hashMaterial = Material.getMaterial(idData[0]);
			if (hashMaterial != null)
				material = hashMaterial;
		} else
			material = Material.getMaterial(Integer.parseInt(idData[0]));

		if (idData.length > 1)
			if (NumberValidator.isShort(idData[1]))
				data = Short.parseShort(idData[1]);
		
		this.material = material;
		this.data = data;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public short getData() {
		return data;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack toItemStack() {
		return new ItemStack(material, 1, data);
	}
	
	@Override
	public String toString() {
		return material.name() + ":" + data;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof DatableMaterial))
			return false;
		
		DatableMaterial datableType = (DatableMaterial) obj;
		return this.material == datableType.material && this.data == datableType.data;
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(material).append(data).toHashCode();
	}
}