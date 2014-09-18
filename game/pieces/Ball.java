package game.pieces;

import game.Player;
import game.pubsub.PieceEvent;
import game.pubsub.Subscriber;
import lab3.Point;
/** 
 * This is one of several subclasses of the Piece class,
 * and is used to represent a ball in the game.
 * Provides a specialized contains method to support more
 * accurate intersection than is supported by the Piece class.
 * 
 * @author Ron Cytron, with some changes by Jon Turner, then by Silas Hsu
 */
public class Ball extends Piece implements Subscriber<PieceEvent> {
	private double radius;
	public final int VALUE; // Points gained if it dies
	public final int RESPAWN_DELAY; // In milliseconds
	
	private Paddle paddle; // The paddle the ball is stuck to, if any.
	private int stuckToPaddleXCoor; // x coordinate of the ball if stuck on the paddle
	private int yDisplaceOnPaddle; // y coordinate of the ball if stuck on the paddle
	
	
	/**
	 * Creates a new Ball with specified owner, default point value of 100 and default
	 * respawn delay of 5000 milliseconds.
	 * 
	 * @param center
	 * @param radius
	 * @param owner - the player that will receive points when the ball hits bricks
	 */
	public Ball(Point center, double radius, Player owner) {
		this(center, radius, owner, 50, 5000);
	}
	
	/**
	 * Creates a new Ball with specified owner, point value, and respawn delay in milliseconds
	 * 
	 * @param center
	 * @param radius
	 * @param owner - the player that will receive points when the ball hits bricks
	 * @param VALUE - points granted when the ball dies
	 * @param RESPAWN_DELAY - in milliseconds
	 */
	public Ball(Point center, double radius, Player owner, int value, int respawnDelay) {
		super(new BoundingBox
				(center,
				(int) (2*radius),
				(int) (2*radius)
				),
			owner);
		this.radius = radius;
		VALUE = value;
		RESPAWN_DELAY = respawnDelay;
	}
	
	@Override
	public boolean contains(Point p) {
		Point center = getCenter();
		return ( (center.getX() - p.getX()) * (center.getX() - p.getX() )
				+ (center.getY() - p.getY()) * (center.getY() - p.getY()) < radius*radius);
			/* Using the distance formula, measures the distance between the center and
			 * p and compares it to the radius
			 */
	}
	
	/**
	 * @return the radius of the ball.
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Shrink the ball by a specified factor and kill it if it
	 * gets too small.
	 * @param factor is multiplied by the ball's radius to get the
	 * new radius; ignored if <0 or >1.
	 */
	public void shrink(double factor) {
		if (factor < 0 || factor > 1) return;
		if (radius * factor < 2.0) die();
		else {
			radius *= factor;
			super.shrink(factor);
		}
	}
	
	/**
	 * Causes the ball to follow the specified paddle.  <b>Be sure the ball
	 * has no velocity!</b>
	 * @param paddle - the ball will stick to this paddle
	 */
	public void stickToPaddle(Paddle paddle) {
		unstickFromPaddle();
		this.paddle = paddle;
		
		if (paddle.getOwner().getId() == 1) {
			stuckToPaddleXCoor = (int) (paddle.getCenter().getX() + paddle.getWidth()/2 + radius);
		}
		else if (paddle.getOwner().getId() == 2) {
			stuckToPaddleXCoor = (int) (paddle.getCenter().getX() - paddle.getWidth()/2 - radius);
		}
		
		yDisplaceOnPaddle = (int) ( getCenter().getY() - paddle.getCenter().getY() );
		setCenter( stuckToPaddleXCoor, (int) getCenter().getY());
		paddle.addSubscriber(this);
	}
	
	/**
	 * Stop the ball from following a paddle previously specified with the stickToPaddle() method.
	 */
	public void unstickFromPaddle() {
		if (paddle != null) {
			paddle.removeSubscriber(this);
			paddle = null;
		}
	}
	
	/**
	 * @return if the ball is stuck on a paddle or not
	 */
	public boolean isStuckOnPaddle() {
		return (paddle != null);
	}
	
	public String toString() { return "ball at " + getCenter(); }

	@Override
	public void observeEvent(PieceEvent e) {
		setCenter(stuckToPaddleXCoor, (int) (paddle.getCenter().getY() + yDisplaceOnPaddle) );
	}
}
