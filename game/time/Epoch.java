package game.time;

/**
 * Represents the differences between two Times.
 * Internally this is kept as milliseconds, but methods
 * are provided to get the answer in seconds 
 *
 */
public class Epoch {
	
	private int duration;
	
	/**
	 * Constructor is private to the package.
	 * @param duration time in milliseconds of the Epoch
	 */
	Epoch(int duration) { this.duration = duration; }
	
	/**
	 * The Epoch in milliseconds
	 * @return the Epoch in milliseconds
	 */
	public int toMillis() { return duration; }
	
	/**
	 * The Epoch in seconds
	 * @return the Epoch in seconds
	 */
	public double toSeconds() { return duration/1000.0; }
}
