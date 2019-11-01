package me.mastercapexd.commands;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class CommandResult {

	public static final CommandResult
	SUCCESS = new CommandResult(0),
	CONSOLE_ONLY = new CommandResult(1),
	PLAYER_ONLY = new CommandResult(2),
	NO_PERMISSION = new CommandResult(3),
	SYNTAX_ERROR = new CommandResult(4);
	
	private final int code;
	
	public CommandResult(int code) {
		this.code = code;
	}
	
	public int getResultCode() {
		return code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof CommandResult))
			return false;
		
		CommandResult result = (CommandResult) obj;
		return result.code == this.code;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(code).build();
	}
}