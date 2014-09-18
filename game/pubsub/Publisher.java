package game.pubsub;

/*
 * Publisher side of the Observer Pattern, implemented
 * by BasePublisher.
 */
public interface Publisher<E extends PubSubEvent> {

	/**
	 * The specified Subscriber will be notified of future events.
	 * @param subscriber
	 */
	public abstract void addSubscriber(Subscriber<E> subscriber);

	/**
	 * The specified Subscriber will no longer be notified of
	 * future events.  No error is thrown if the subscriber is
	 * not currently subscribing.
	 * @param subscriber
	 */
	public abstract void removeSubscriber(Subscriber<E> subscriber);

	/**
	 * Broadcast the specified PubSubEvent to all subscribers.
	 * @param e
	 */
	public abstract void notifySubscribers(E e);

}
