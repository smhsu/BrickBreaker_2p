package game.motion;

import game.time.Clock;
import game.time.Time;
import lab3.Point;
import lab3.Vector;

/**
 * An object sitting still, with zero velocity and acceleration.
 * This is the simplest of the three velocity classes.
 */
public class Stationary implements Trajectory {

	private Point origin;
	private Time t0;
	
	/**
	 * Creates a trajectory with no velocity or acceleration.  The object will be stopped at its current
	 * location based on its current trajectory.
	 * @param old - the Trajectory of an object
	 */
	public Stationary(Trajectory old) {
		this(old.getCurrentLocation());
	}
	
	/**
	 * Creates a trajectory with no velocity or acceleration
	 * @param origin - point at which the Trajectory begins
	 */
	public Stationary(Point origin) {
		this.origin = origin;
		t0 = Clock.instance().currentTime();
	}
	
	@Override
	public Time getT0() {
		return t0;
	}
	
	@Override
	final public Point getCurrentLocation() {
		return getLocation(Clock.instance().currentTime());
	}

	public Point getLocation(Time t) { return origin; }
	
	public Vector getCurrentVelocity() {
		return new Vector(0,0);
	}
	
	public Vector getCurrentAcceleration() {
		return new Vector(0,0);
	}
}
