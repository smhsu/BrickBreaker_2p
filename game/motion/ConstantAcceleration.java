package game.motion;

import game.time.Clock;
import game.time.Time;
import lab3.Point;
import lab3.Vector;

/**
 * Extends ConstantVelocity by contributing a constant (but not necessarily
 * zero) acceleration.
 * 
 * @author Ron Cytron
 */
public class ConstantAcceleration extends ConstantVelocity {

	private Vector acceleration;
	
	/**
	 * Constructs a new Trajectory based on the current location of the supplied one.
	 * @param old Trajectory for computing the current location.
	 * @param v Velocity of the new Trajectory
	 * @param a Acceleration of the new Trajectory
	 */

	public ConstantAcceleration(Trajectory old, Vector v, Vector a) {
		this(old.getCurrentLocation(), v, a);
	}
	
	/**
	 * A new Trajectory based on the point of origin, a velocity, and an acceleration
	 * @param origin Point of origin
	 * @param v original velocity
	 * @param a constant acceleration
	 */
	public ConstantAcceleration(Point origin, Vector v, Vector a) {
		super(origin, v);
		this.acceleration = a;
	}
	
	/**
	 * Current location computed as origin + vt + 1/2at^2
	 */
	@Override
	public Point getLocation(Time t) {
		double deltaT = Clock.instance().currentTime().timeSince(getT0()).toSeconds();
		return super.getLocation(t).plus(acceleration.scale(0.5*deltaT*deltaT));
	}
	
	/**
	 * Current velocity computed as v + at
	 */
	@Override
	public Vector getCurrentVelocity() {
		double deltaT = Clock.instance().currentTime().timeSince(getT0()).toSeconds();
		return super.getCurrentVelocity().plus(acceleration.scale(deltaT));
	}

	/**
	 * Current acceleration
	 */
	@Override
	public Vector getCurrentAcceleration() {
		return acceleration;
	}

}
