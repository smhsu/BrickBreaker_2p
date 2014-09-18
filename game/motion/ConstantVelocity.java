package game.motion;

import game.time.Clock;
import game.time.Time;
import lab3.Point;
import lab3.Vector;

/**
 * Extends Stationary by contributing a constant (but not 
 * necessarily zero) velocity. It may seem counter-intuitive
 * that this is a sub-class of stationary, since logically,
 * the universe of constant velocity objects is a superset
 * of the universe of stationary object. What matters here
 * is the fact that ConstantVelocity objects have an additional
 * attribute (a non-zero velocity) that stationary objects do
 * not have.
 * 
 * @author Ron Cytron
 */
public class ConstantVelocity extends Stationary {

	private Vector velocity;
	
	/**
	 * Constructs a new Trajectory based on the current location of the supplied one.
	 * @param old - Trajectory for computing the current location.
	 * @param v - Velocity of the new Trajectory
	 */
	public ConstantVelocity(Trajectory old, Vector v) {
		this(old.getCurrentLocation(), v);
	}
	
	/**
	 * A Trajectory with constant velocity.
	 * @param origin - Point at which the Trajectory begins
	 * @param v - constant velocity for this Trajectory
	 */
	public ConstantVelocity(Point origin, Vector v) {
		super(origin);
		this.velocity = v;
	}
	
	/**
	 * Location at time t is computed as point of origin + vt, where v is the
	 *   velocity.
	 */
	@Override
	public Point getLocation(Time t) {
		double deltaT = Clock.instance().currentTime().timeSince(getT0()).toSeconds();
		return super.getLocation(t).plus(velocity.scale(deltaT));
	}
	
	/**
	 * Current velocity.
	 */
	@Override
	public Vector getCurrentVelocity() {
		return super.getCurrentVelocity().plus(velocity);
	}

}
