package game.motion;

import lab3.Vector;
import game.*;
import game.time.*;
import game.pieces.Piece;
import game.pubsub.*;

/**
 * At each tick, set the location of the Piece according
 *   to the specified Trajectory.
 *
 * @author unknown; some modifications by Silas Hsu
 */
public class PieceMover implements Subscriber<ClockTick> {
	
	private Piece piece;
	private Trajectory trajectory;
	private boolean dead;
	
	public PieceMover(Piece p, Trajectory t) {
		this.piece = p;
		this.trajectory = t;
		this.dead = false;
		Clock.instance().addSubscriber(this);
	}
	
	/**
	 * In response to a clock tick, set the location of the
	 * Relocatable to the current location of the Trajectory.
	 */
	@Override
	public void observeEvent(ClockTick e) {
		if (piece.isDead()) {die(); Clock.instance().removeSubscriber(this); }
		if (trajectory.getCurrentVelocity().isZero() && trajectory.getCurrentAcceleration().isZero()) { // This is so outside setCenter() calls will still work
			return;
		}
		piece.setCenter(trajectory.getCurrentLocation());				
	}

	/**
	 * Change the Trajectory associated with this piece to a new 
	 * one whose velocity's x and y components are multiplied
	 * by xfactor and yfactor, respectively. As one example,
	 * if xfactor=0.5 and yfactor=-1, the piece moves half as fast
	 * to the left or right, and reverses direction its up-down
	 * motion.
	 * @param xfactor
	 * @param yfactor
	 */
	public void bounce(double xfactor, double yfactor) {
		Vector v = trajectory.getCurrentVelocity();
		Vector a = trajectory.getCurrentAcceleration();
		v = new Vector(v.getDeltaX()*xfactor, v.getDeltaY()*yfactor);
		this.trajectory = new ConstantAcceleration(this.trajectory, v, a);
	}
	
	/**
	 * Change the Trajectory associated with this piece to a new one whose direction
	 * is the specified vector and whose speed is multiplied by the specified factor.
	 * 
	 * @param newDirection
	 * @param factor
	 */
	public void redirect(Vector newDirection, double factor) {
		if (factor == 0) {
			this.trajectory = new Stationary(trajectory);
			return;
		}
		Double currentMagnitude = trajectory.getCurrentVelocity().magnitude();
		Vector a = trajectory.getCurrentAcceleration();
		Vector newVelocity = newDirection.rescale(currentMagnitude * factor);
		this.trajectory = new ConstantAcceleration(this.trajectory, newVelocity, a);
	}
	
	public void setTrajectory(Trajectory t) {
		this.trajectory = t;
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	public void die() {
		dead = true;
	}

	public Vector getTrajectory() {
		return trajectory.getCurrentVelocity();
	}
	
}
