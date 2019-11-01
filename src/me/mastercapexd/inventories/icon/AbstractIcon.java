package me.mastercapexd.inventories.icon;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import me.mastercapexd.inventories.ClickData;

public abstract class AbstractIcon implements Icon {

	private final Consumer<ClickData> clickAction;
	private final boolean takeable;
	
	public AbstractIcon(@Nonnull Consumer<ClickData> clickAction, boolean takeable) {
		this.clickAction = clickAction;
		this.takeable = takeable;
	}
	
	@Nonnull
	@Override
	public Consumer<ClickData> getClickAction() {
		return clickAction;
	}
	
	@Override
	public boolean isTakeable() {
		return takeable;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Icon))
			return false;
		
		Icon icon = (Icon) obj;
		return this.getIcon().equals(icon.getIcon()) && this.clickAction == icon.getClickAction() && this.takeable == icon.isTakeable();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(clickAction).append(takeable).append(getIcon()).build();
	}
}