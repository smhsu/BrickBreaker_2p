package game.time;

import game.pubsub.PubSubEvent;

/**
 * Event container for a clock tick includes just the time of the tick.
 *
 */
public class ClockTick implements PubSubEvent {
	
	final private Time t;
	
	public ClockTick(Time t) {
		this.t = t;
	}
	
	/**
	 * Returns the Time of the ClockTick event.
	 * @return Time of the ClockTick event.
	 */
	public Time getTimeAtTick() {
		return t;
	}

}
