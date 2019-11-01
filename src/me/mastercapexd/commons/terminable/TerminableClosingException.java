package me.mastercapexd.commons.terminable;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class TerminableClosingException extends Exception {

	private static final long serialVersionUID = 4849871693685578333L;
	
	private final List<? extends Throwable> causes;
	
	public TerminableClosingException(Collection<? extends Throwable> causes) {
		super("Exception(s) occurred whilst closing: " + causes.toString());
		if (causes.isEmpty())
			throw new IllegalArgumentException("No causes");
		this.causes = ImmutableList.copyOf(causes);
    }
	
	public List<? extends Throwable> getCauses() {
		return this.causes;
	}
	
	public void printAllStackTraces() {
		this.printStackTrace();
		for (Throwable cause : this.causes)
			cause.printStackTrace();
	}
}