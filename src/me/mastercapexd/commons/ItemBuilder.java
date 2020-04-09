package me.mastercapexd.commons;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.mastercapexd.commons.DatableMaterial;
import me.mastercapexd.commons.colors.Colors;
import me.mastercapexd.commons.util.Builder;
import me.mastercapexd.commons.util.EnumUtils;

import com.google.common.collect.Lists;

@SuppressWarnings("deprecation")
public final class ItemBuilder implements Builder<ItemStack> {

	public static List<String> toStringList(@Nonnull ItemStack itemStack) {
		List<String> list = Lists.newArrayList();
		ItemMeta meta = itemStack.getItemMeta();
		
		list.add("name:" + itemStack.getType().name());
		list.add("durability:" + itemStack.getDurability());
		list.add("amount:" + itemStack.getAmount());
		if (meta != null) {
			if (meta.hasDisplayName())
				list.add("display:" + meta.getDisplayName());
			if (meta.hasLore())
				list.add("lore:" + StringUtils.join(meta.getLore(), "%n"));
			if (meta.hasEnchants()) {
				Collection<String> enchantments = meta.getEnchants().entrySet().stream().map(entry -> entry.getKey().getName() + "^" + entry.getValue()).collect(Collectors.toSet());
				list.add("ench:" + StringUtils.join(enchantments, "%n"));
			}
			list.add("flags:" + StringUtils.join(meta.getItemFlags().stream().map(ItemFlag::name).collect(Collectors.toSet()), "%n"));
			if (meta instanceof SkullMeta) {
				SkullMeta skullMeta = (SkullMeta) meta;
				if (skullMeta.getOwner() != null)
					list.add("owner:" + skullMeta.getOwner());
			}
			if (meta.isUnbreakable())
				list.add("unbreakable:" + meta.isUnbreakable());
		}
		return list;
	}
	
	public static String[] toStringArray(@Nonnull ItemStack itemStack) {
		List<String> list = toStringList(itemStack);
		return list.toArray(new String[list.size()]);
	}
	
	public static String toStringFormat(@Nonnull ItemStack itemStack) {
		List<String> list = toStringList(itemStack);
		return StringUtils.join(list, ";");
	}
	
	public static List<String> toStringList(@Nullable String string) {
		return Lists.newArrayList(toStringArray(string));
	}
	
	public static String[] toStringArray(@Nonnull String string) {
		return string.split(";");
	}
	
	public static ItemStack toItemStack(@Nonnull String string) {
		ItemBuilder builder = null;
		
		if (!string.startsWith("type:"))
			throw new IllegalArgumentException("Parsed ItemStack must start with type!");
		
		for (String str : toStringArray(string)) {
			if (str.startsWith("type:"))
				builder = new ItemBuilder(Material.valueOf(str.replace("type:", "")));
			else if (str.startsWith("durability:"))
				builder.setDurability(Short.parseShort(str.replace("durability:", "")));
			else if (str.startsWith("amount:"))
				builder.setAmount(Integer.parseInt(str.replace("amount:", "")));
			else if (str.startsWith("display:"))
				builder.setDisplayName(Colors.colorize(str.replace("display:", "")));
			else if (str.startsWith("lore:"))
				builder.setLore(Lists.newArrayList(str.replace("lore:", "").split("%n")));
			else if (str.startsWith("ench:")) {
				String[] ench = str.replace("ench:", "").split("%n");
				for (String e : ench) {
					String[] data = e.split(Pattern.quote("^"));
					builder.addEnchant(Enchantment.getByName(data[0]), Integer.parseInt(data[1]), true);
				}
			} else if (str.startsWith("flags:"))
				builder.addItemFlags(Arrays.stream(str.replace("flags:", "").split("%n")).map(s -> ItemFlag.valueOf(s)).collect(Collectors.toList()));
			else if (str.startsWith("owner:"))
				builder.setOwner(str.replace("owner:", ""));
			else if (str.startsWith("unbreakable:"))
				builder.setUnbreakable(Boolean.valueOf(str.replace("unbreakable:", "")));
		}
		return builder.build();
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
		if (item.getType() != Material.valueOf(EnumUtils.getEnum(Material.class, "SKULL_ITEM") == null ? "LEGACY_SKULL_ITEM" : "SKULL_ITEM") || item.getDurability() != 3)
			return this;
		
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
		skullMeta.setOwner(ownerName);
		item.setItemMeta(skullMeta);
		return this;
	}
	
	public String getOwner() {
		if (item.getItemMeta() instanceof SkullMeta)
			return ((SkullMeta) item.getItemMeta()).getOwner();
		return null;
	}
	
	public ItemBuilder setUnbreakable(boolean unbreakable) {
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(unbreakable);
		item.setItemMeta(meta);
		return this;
	}
	
	public boolean isUnbreakable() {
		return item.getItemMeta().isUnbreakable();
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