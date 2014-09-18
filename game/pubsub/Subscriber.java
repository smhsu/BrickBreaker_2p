package game.pubsub;

public interface Subscriber<E> {
	
	/**
	 * Method that is called by a Publisher when the specified
	 *   PubSubEvent has occurred.
	 * @param e The PubSubEvent, varies by Publisher.
	 */
	public abstract void observeEvent(E e);
	
	/**
	 * Subscribers must be able to say when they are still alive
	 *    or are dead.
	 * @return true if this Subscriber is no longer interested in
	 *    receiving notifications of events from its Publisher.
	 */
	public abstract boolean isDead();

}
