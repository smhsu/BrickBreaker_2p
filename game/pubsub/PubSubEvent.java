package game.pubsub;

/**
 * A generic interface for events. Publishers will implement and
 * extend this to provide the payload they need for events.
 * This interface may seem pointless, since it has no methods
 * that implementing classes are required to provide. However,
 * it is useful because it still effectively limits the classes
 * that can be used as PubSubEvents to those that explicitly
 * state that they implement the interface.
 */
public interface PubSubEvent {

}
