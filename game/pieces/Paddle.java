package game.pieces;

import game.Player;
import game.collision.BallHitsPaddle;
import nip.GraphicsPanel;
import lab3.Point;

/**
 * This is one of several subclasses of the Piece class,
 * and is used to represent a paddle in the game.
 * 
 * @author Ron Cytron, with some changes by Jon Turner, then some changes by Silas Hsu
 */
public class Paddle extends Piece {
	
	private int upLimit;
	private int downLimit;
	public boolean isSticky;
	public BallHitsPaddle collisionDetector;
	
	/**
	 * Creates a new paddle
	 * 
	 * @param center
	 * @param width
	 * @param height
	 * @param upLimit - the lowest Y coordinate that any part of the paddle may have
	 * @param downLimit - the highest Y coordinate that any part of the paddle may have
	 * @param owner - the Player that controls the paddle
	 */
	public Paddle(Point center, int width, int height, int upLimit, int downLimit, Player owner) {
		super(new BoundingBox(center, width, height), owner);
		this.upLimit = upLimit;
		this.downLimit = downLimit;
		isSticky = false;
	}
	
	/**
	 * Sets the center of the paddle.  The paddle's y coordinate is limited to the
	 * interior of the playing field.
	 * @param p - new center of the paddle
	 */
	public void setCenter(Point p) {
		if (p.getY() < upLimit + getHeight()/2) // If the paddle would go off the top
			super.setCenter(new Point
					(p.getX(),
					upLimit + getHeight()/2));
		
		else if (p.getY() > downLimit - getHeight()/2) // If the paddle would go off the bottom
			super.setCenter(new Point 
					(p.getX(),
					downLimit - getHeight()/2));
		
		else
			super.setCenter(p);
	}
	
	/**
	 * Returns if the paddle has a ball stuck on it by checking if its collision detector
	 * field has been initialized (it should only be initialized if a ball hits the paddle
	 * when <code>isSticky</code> is true).
	 * 
	 * @return if the paddle has a ball stuck on it
	 */
	public boolean hasBallStuck (){
		return (collisionDetector != null);
	}

	public String toString() {
		return "paddle at " + getCenter();
	}
}
