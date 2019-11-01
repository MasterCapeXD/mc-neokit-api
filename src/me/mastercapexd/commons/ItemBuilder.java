package me.mastercapexd.commons;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.Builder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.mastercapexd.commons.DatableMaterial;
import com.google.common.collect.Lists;

public final class ItemBuilder implements Builder<ItemStack> {

	public static String toStringFormat(@Nonnull ItemStack stack) {
		Material material = stack.getType();
		short durability = stack.getDurability();
		int amount = stack.getAmount();
		String dn = stack.getItemMeta().getDisplayName();
		String lore = "null";
		if (stack.getItemMeta().getLore() != null) {
			lore = "";
			for (String line : stack.getItemMeta().getLore())
				lore = lore + line + "%nl%";
			lore = lore.substring(0, lore.length() - 4);
		}
		
		String enchantments = "null";
		if (!stack.getEnchantments().isEmpty()) {
			enchantments = "";
			for (Enchantment ench : stack.getEnchantments().keySet())
				enchantments = enchantments + ench.getName() + "%equals%" + stack.getEnchantments().get(ench) + "%nxt_ench%";
			enchantments = enchantments.substring(0, enchantments.length() - 10);
		}
		
		return material.name() + "%el%" + durability + "%el%" + amount + "%el%" + dn + "%el%" + lore + "%el%" + enchantments;
	}
	
	public static ItemStack toItemStack(@Nonnull String string) {
		String[] stringData = string.split("%el%");
		Material material = Material.valueOf(stringData[0]);
		short durability = Short.parseShort(stringData[1]);
		int amount = Integer.parseInt(stringData[2]);
		String dn = stringData[3];
		String loreString = stringData[4];
		String enchantments = stringData[5];
		
		List<String> lore = Lists.newArrayList();
		String[] loreStringArray = loreString.split("%nl%");
		for (String str : loreStringArray)
			lore.add(str);
		
		if (dn.equalsIgnoreCase("null"))
			dn = null;
		if (loreString.equalsIgnoreCase("null"))
			lore = null;
		
		ItemStack parsingStack = new ItemStack(material, amount);
		parsingStack.setDurability(durability);
		ItemMeta meta = parsingStack.getItemMeta();
		meta.setDisplayName(dn);
		meta.setLore(lore);
		if (!enchantments.equalsIgnoreCase("null")) {
			String[] enchantmentsArray = enchantments.split("%nxt_ench%");
			for (String ench : enchantmentsArray) {
				String[] enchData = ench.split("%equals%");
				meta.addEnchant(Enchantment.getByName(enchData[0]), Integer.parseInt(enchData[1]), true);
			}
		}
		parsingStack.setItemMeta(meta);
		return parsingStack;
	}
	
	private final ItemStack item;
	
	public ItemBuilder(@Nonnull ItemStack itemStack) {
		this.item = new ItemStack(itemStack);
	}

	public ItemBuilder(@Nonnull DatableMaterial datableMaterial) {
		this.item = new ItemStack(datableMaterial.getMaterial(), 1, datableMaterial.getData());
	}
	
	public ItemBuilder(@Nonnull Material material) {
		this.item = new ItemStack(material);
	}
	
	public ItemBuilder setType(Material material) {
		item.setType(material);
		return this;
	}
	
	public ItemBuilder setType(DatableMaterial datableMaterial) {
		item.setType(datableMaterial.getMaterial());
		item.setDurability(datableMaterial.getData());
		return this;
	}
	
	public Material getType() {
		return item.getType();
	}
	
	public ItemBuilder setDurability(short value) {
		item.setDurability(value);
		return this;
	}
	
	public short getDurability() {
		return item.getDurability();
	}
	
	public ItemBuilder setAmount(int amount) {
		if (amount < 1)
			item.setType(Material.AIR);
		else
			item.setAmount(amount);
		return this;
	}
	
	public ItemBuilder add(int amount) {
		int newAmount = item.getAmount() + amount;
		return setAmount(newAmount);
	}
	
	public ItemBuilder subtract(int amount) {
		int newAmount = item.getAmount() - amount;
		return setAmount(newAmount);
	}
	
	public int getAmount() {
		return item.getAmount();
	}
	
	public ItemBuilder setDisplayName(String displayName) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		item.setItemMeta(itemMeta);
		return this;
	}
	
	public boolean hasDisplayName() {
		return item.getItemMeta().hasDisplayName();
	}
	
	public String getDisplayName() {
		return item.getItemMeta().getDisplayName();
	}
	
	public ItemBuilder setLore(List<String> lore) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemBuilder setLore(String... lines) {
		return setLore(Arrays.asList(lines));
	}
	
	public boolean hasLore() {
		return item.getItemMeta().hasLore();
	}
	
	public List<String> getLore() {
		return item.getItemMeta().getLore();
	}
	
	public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addEnchant(enchantment, level, ignoreLevelRestriction);
		item.setItemMeta(itemMeta);
		return this;
	}
	
	public boolean hasEnchantment(Enchantment enchantment) {
		return item.getItemMeta().hasEnchant(enchantment);
	}

	public boolean hasConflictingEnchant(Enchantment enchantment) {
		return item.getItemMeta().hasConflictingEnchant(enchantment);
	}
	
	public boolean isEnchanted() {
		return item.getItemMeta().hasEnchants();
	}

	public int getLevelOf(Enchantment enchantment) {
		return item.getItemMeta().getEnchantLevel(enchantment);
	}
	
	public Map<Enchantment, Integer> getEnchants() {
		return item.getItemMeta().getEnchants();
	}

	public ItemBuilder removeEnchant(Enchantment enchantment) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.removeEnchant(enchantment);
		item.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemBuilder addItemFlags(ItemFlag... flags) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addItemFlags(flags);
		item.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemBuilder addItemFlags(List<ItemFlag> listOfFlags) {
		return addItemFlags(listOfFlags.toArray(new ItemFlag[listOfFlags.size()]));
	}
	
	public boolean hasItemFlag(ItemFlag itemFlag) {
		return item.getItemMeta().hasItemFlag(itemFlag);
	}
	
	public ItemBuilder removeItemFlags(ItemFlag... flags) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.removeItemFlags(flags);
		item.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemBuilder removeItemFlags(List<ItemFlag> listOfFlags) {
		return removeItemFlags(listOfFlags.toArray(new ItemFlag[listOfFlags.size()]));
	}

	public ItemBuilder setOwner(String ownerName) {
		if (item.getType() != Material.SKULL_ITEM || item.getDurability() != 3)
			return this;
		
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
		skullMeta.setOwner(ownerName);
		item.setItemMeta(skullMeta);
		return this;
	}
	
	@Override
	public ItemStack build() {
		return item;
	}
	
	@Override
	public String toString() {
		return "{ItemBuilder} = " + item.toString();
	}
}