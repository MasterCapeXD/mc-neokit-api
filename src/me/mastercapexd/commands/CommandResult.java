package me.mastercapexd.commands;

import org.apache.commons.lang.builder.HashCodeBuilder;

import me.mastercapexd.commons.Identifiable;

public final class CommandResult implements Identifiable<String> {

	public static final CommandResult
	SUCCESS = new CommandResult("SUCCESS"),
	CONSOLE_ONLY = new CommandResult("CONSOLE_ONLY"),
	PLAYER_ONLY = new CommandResult("PLAYER_ONLY"),
	NO_PERMISSION = new CommandResult("NO_PERMISSION");
	
	private final String id;
	
	public CommandResult(String id) {
		this.id = id;
	}
	
	@Override
	public String getIdentifier() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof CommandResult))
			return false;
		
		CommandResult result = (CommandResult) obj;
		return result.id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}