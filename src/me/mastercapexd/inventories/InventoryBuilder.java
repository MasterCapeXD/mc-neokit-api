package me.mastercapexd.inventories;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.Builder;

public interface InventoryBuilder<B extends InventoryBuilder<B, R>, R extends InventoryBase> extends Builder<R> {

	@Nonnull
	B setRows(int rows);
	
	@Nonnull
	B addOpeningAction(@Nonnull Consumer<InventoryData> consumer);
	
	@Nonnull
	B addClosingAction(@Nonnull Consumer<InventoryData> consumer);
}