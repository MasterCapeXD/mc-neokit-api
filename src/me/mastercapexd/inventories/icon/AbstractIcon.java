package me.mastercapexd.inventories.icon;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import me.mastercapexd.inventories.ClickData;

public abstract class AbstractIcon implements Icon {

	private final Predicate<ClickData> clickAction;
	
	public AbstractIcon(@Nonnull Predicate<ClickData> clickAction) {
		this.clickAction = clickAction;
	}
	
	@Nonnull
	@Override
	public Predicate<ClickData> getClickAction() {
		return clickAction;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Icon))
			return false;
		
		Icon icon = (Icon) obj;
		return this.getIcon().equals(icon.getIcon()) && this.clickAction == icon.getClickAction();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(clickAction).append(getIcon()).build();
	}
}