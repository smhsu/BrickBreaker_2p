package game.motion;

import game.time.Time;
import lab3.Point;
import lab3.Vector;

/**
 * An interface useful for things in motion.  This is implemented by
 * Stationary, ConstantVelocity, and ConstantAcceleration.
 */
public interface Trajectory {
	
	/**
	 * Time when this Trajectory began
	 * @return time origin of this Trajectory
	 */
	public Time getT0();
	
	/**
	 * Location at a given time.
	 * @param t time at which location should be computed, relative to time origin.
	 * @return current location at time t
	 */
	public Point getLocation(Time t);
	
	/**
	 * Current location
	 * @return current location
	 */
	public Point getCurrentLocation();
	
	/**
	 * Current velocity
	 * @return current velocity
	 */
	public Vector getCurrentVelocity();
	
	/**
	 * Current acceleration
	 * @return current acceleration
	 */
	public Vector getCurrentAcceleration();

}
