package game.pubsub;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Simple implementation of a Publisher that keeps track of
 * Subscribers using a LinkedList
 *
 * @param <E> the event type for notification
 */
public class BasePublisher<E extends PubSubEvent> implements Publisher<E> {
	
	private LinkedList<Subscriber<E>> list;
	
	public BasePublisher() {
		list = new LinkedList<Subscriber<E>>();
	}
	
	/**
	 * Add a new subscriber.
	 * 
	 * @param subscriber is the new subscriber being added
	 */
	public void addSubscriber(Subscriber<E> subscriber) {
		list.add(subscriber);
	}
	
	/**
	 * Remove a subscriber.
	 * 
	 * @param subscriber is the subscriber being removed
	 */
	public void removeSubscriber(Subscriber<E> subscriber) {
		list.remove(subscriber);
	}

	/**
	 * Informs all subscribers than an event has occured by
	 * calling their observeEvent() method.
	 * 
	 * @param e the event to be passed onto the subscribers
	 */
	final synchronized public void notifySubscribers(E e) {	
		// Create a separate copy of the subscriber list before starting
		// the notification process. There are two reasons for doing
		// this.
		//    1) Some subscribers may have died and need to be removed
		//    2) A call to observeEvent() could indirectly lead to an 
		//	     addSubscriber() call on this object. If we were iterating
		//       over the subscriber list at that time, we could get
		// 	     a ConcurrentModificationException. To be safe, we
		//       iterate over a copy of the subscriber list.
		//
		LinkedList<Subscriber<E>> copy = new LinkedList<Subscriber<E>>();
		Iterator<Subscriber<E>> iter = list.iterator();
		while (iter.hasNext()) {
			Subscriber<E> s = iter.next();
			if (s.isDead()) iter.remove();
			else copy.add(s);
		}
		
		//  Use the copy in case notification triggers an add to the main list
		//
		for (Subscriber<E> s : copy)  s.observeEvent(e);
	}
}
