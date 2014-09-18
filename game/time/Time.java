package game.time;

/**
 * Represents Time, but an Epoch is the externally useful concept.
 */
public class Time {

	private int t;
	
	public Time(int t) { this.t = t; }
	
	/**
	 * Returns the time span from before to now
	 * @param before origin of Epoch
	 * @return Epoch of time from before to now
	 */
	public Epoch timeSince(Time before) {
		return new Epoch(this.t - before.t);
	}	
}
