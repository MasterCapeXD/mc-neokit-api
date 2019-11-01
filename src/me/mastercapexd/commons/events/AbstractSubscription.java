package me.mastercapexd.commons.events;

public abstract class AbstractSubscription implements Subscription {

	private final long millis;
	private int calls;
	
	public AbstractSubscription() {
		this.millis = System.currentTimeMillis();
	}
	
	protected void setSubscriptionCalls(int calls) {
		this.calls = calls;
	}
	
	protected void addNewCall() {
		calls++;
	}
	
	@Override
	public int calls() {
		return calls;
	}
	
	@Override
	public long subscriptionTimeMillis() {
		return millis;
	}
}