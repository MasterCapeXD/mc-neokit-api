package me.mastercapexd.inventories.paginated;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

import me.mastercapexd.inventories.personal.PersonalInventoryBuilder;

public interface PaginatedInventoryBuilder extends PersonalInventoryBuilder {

	@Nonnull
	PaginatedInventoryBuilder setContentSlotsScheme(@Nonnull Integer[] slots);
	
	@Nonnull
	default PaginatedInventoryBuilder setContentSlotsScheme(@Nonnull String scheme) {
		String[] numbers = scheme.split(" ");
		Preconditions.checkArgument(StringUtils.isNumeric(StringUtils.join(numbers)), "Scheme must contain only numeric characters!");
		
		Integer[] slots = new Integer[scheme.length()];
		for (int i = 0; i < scheme.length(); i++)
			slots[i] = Integer.parseInt(numbers[i]);
		
		return setContentSlotsScheme(slots);
	}
}