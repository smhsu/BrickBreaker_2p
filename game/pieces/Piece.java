package game.pieces;

import game.Player;
import game.pubsub.*;
import java.util.LinkedList;
import lab3.Point;
import lab3.Vector;
/**
 * The Piece class is used to represent various components in
 * the game, including bricks, balls, the paddle and the
 * boundaries of the game. It provides methods to move a
 * piece and if this piece interacts with another. It also
 * supports adjustment to its size and to its "life status".
 * 
 * @author Jon Turner, adapted from original by Ron Cytron, also some changes by Silas Hsu
 */
public class Piece extends BasePublisher<PieceEvent> {
	
	protected BoundingBox bb;
	private double status; // in [0,1], 0 when dead,  1 when fully alive
	private Vector direction; // direction in which piece most recently moved
	private Player owner;
	
	/**
	 * Creates a piece with no owner
	 * @param bb
	 */
	public Piece(BoundingBox bb) {
		this(bb, null);
	}
	
	/**
	 * Creates a piece with the specified owner.
	 *
	 * @param bb
	 * @param owner
	 */
	public Piece(BoundingBox bb, Player owner) {
		super();
		this.bb = bb;
		status = 1;
		direction = new Vector(0,0);
		this.owner = owner;
	}
	
	/**
	 * @return the width of the piece
	 */
	public int getWidth() { return bb.getWidth(); }

	/**
	 * @return the height of the piece
	 */
	public int getHeight() { return bb.getHeight(); }
	/**
	 * @return the center of the piece
	 */
	public Point getCenter() { return bb.getCenter(); }
	
	/**
	 * @return the bounding box for the piece
	 */
	public BoundingBox getBB() { return this.bb; }

	/**
	 * Set the bounding box for the piece.
	 * @param bb is the new bounding box
	 */
	public void setBB(BoundingBox bb) { this.bb = bb; }

	/**
	 * @return the "life status" of the piece
	 */
	public double getStatus() { return status; }

	/**
	 * @return true if the piece is dead
	 */
	public boolean isDead() { return status == 0; }
	
	/**
	 * @return the direction in which the piece last moved;
	 * more precisely, the vector difference between the last
	 * two center points.
	 */
	public Vector getDirection() { return direction; }
	
	/**
	 * @return the Player associated with this piece
	 */
	public Player getOwner() { return owner; }

	/**
	 * The intersects method is used to determine when two pieces
	 * have come into contact with one another. It does this by first
	 * comparing the piece's bounding boxes (which is fast) and then
	 * for pieces whose bounding boxes intersect, it compares
	 * the sets of points with integer coordinates that are inside the
	 * pieces. This part, it can be slow if both pieces are really large.
	 * @param other the second piece which is checked against this for intersection
	 * @return true if the two pieces intersect, else false.
	 */
	public boolean intersects(Piece other) {
		// If boxes don't intersect, neither can the Pieces
		if (!this.bb.intersects(other.bb)) return false;

		Piece bigger = this;
		Piece smaller = other;

		if (bigger.bb.area() < smaller.bb.area()) {
			bigger = other;
			smaller = this;
		}

		// Iterating through smaller set of points can be faster.
		for (Point p : smaller.points()) {
			if (bigger.contains(p))
				return true;
		}
		return false;
	}

	/**
	 * A subclass can override this method to produce more precise
	 * intersection calculation.
	 * @param p is point to be tested for containment
	 * @return true if p is a point inside the piece
	 */
	public boolean contains(Point p) { return bb.contains(p); }
	
	/**
	 * Returns a list of points that are inside the piece.
	 * It does this by checking which points in the bounding box
	 * are actually contained in the piece.
	 * @return
	 */
	public Iterable<Point> points() {
		LinkedList<Point> list = new LinkedList<Point>();
		for (Point p : bb.points()) {
			if (this.contains(p))
				list.add(p);
		}
		return list;
	}

	/**
	 * Causes the piece to die.
	 */
	public void die() { 
		dieSome(0);
	}
	
	/**
	 * Multiplies the life status of the piece by a specified factor
	 * and informs all game components that are subscribers of this
	 * piece.
	 * @param factor that is multiplied by the life status; ignored
	 * if less than 0 or greater than 1
	 */
	public void dieSome(double factor) {
		if (factor < 0 || factor > 1) return; // ignore bad argument
		status *= factor;
		if (status < .1) status = 0;
		this.notifySubscribers(new PieceEvent(this));
	}
	
	/**
	 * Adds the specified number to the life status of the piece
	 * and informs all game components that are subscribers of this
	 * piece.  A negative parameter will subtract life status (make
	 * the piece closer to death) and a positive parameter will add
	 * life status (make the piece more alive).  Status is capped
	 * between 0 and 1.
	 * @param addend number to add to the piece's status; status is capped
	 * between 0 and 1.
	 */
	public void augmentLifeStatus(double addend) {
		status += addend;
		if (status > 1) status = 1;
		if (status < 0) status = 0;
		this.notifySubscribers(new PieceEvent(this));
	}
	
	/**
	 * Changes the location of the the piece's center and informs subscribers of
	 * the change in location.
	 * @param p new location for the center of the piece
	 */
	public void setCenter(Point p) {
		if (!p.equals(bb.getCenter())) {
			direction = new Vector(p.getX()-getCenter().getX(),
					   p.getY()-getCenter().getY());
			setBB(new BoundingBox(p,getWidth(),getHeight()));			
			this.notifySubscribers(new PieceEvent(this));
		} 
	}
	
	/**
	 * Changes the location of the piece's center and informs subscribers
	 * of the change in location
	 * @param x - x coordinate of the new center
	 * @param y - y coordinate of the new center
	 */
	public void setCenter(int x, int y) {
		setCenter(new Point(x, y));
	}
	
	/**
	 * Informs subscribers that the piece has shrunk.
	 * Subclasses are expected to provide their own shrink
	 * class to effect the change in size, then invoke the
	 * method in the superclass.
	 * @param factor by which the size is changed
	 */
	public void shrink(double factor) {
		this.notifySubscribers(new PieceEvent(this));
	}
}
