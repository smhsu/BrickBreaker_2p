package game.collision;

import game.*;
import game.pieces.Piece;
import game.pubsub.*;

/**
 * Base class for objects that react to collisions.
 * A CollisionHandler is responsible for monitoring two components of
 * the game and responding when they collide with each other.
 * To to this, it creates an associated CollisionDetector and
 * subscribes to the detector, in order receive collision reports.
 *
 * @author Ron Cytron, with changes by Jon Turner
 */
abstract public class CollisionHandler implements Subscriber<Collision> {
	private boolean dead;
	
	public CollisionHandler(Piece one, Piece two) {
		this(one, two, 10);
		dead = false;
	}

	public CollisionHandler(Piece one, Piece two, int delay) {
		new CollisionDetector(one, two, delay).addSubscriber(this);
	}
	
	/**
	 * This is left to the extending classes of CollisionHandler
	 */
	abstract public void observeEvent(Collision e);
	
	public boolean isDead() { return dead; }
	
	public void die() { dead = true; }
}
