package me.mastercapexd.commons.events;

import me.mastercapexd.commons.function.Terminable;

public interface Subscription extends Terminable {

	void expire();
	
	boolean expired();
	
	int calls();
	
	long subscriptionTimeMillis();
	
	@Override
	default void terminate() throws Exception {
		expire();
	}
}